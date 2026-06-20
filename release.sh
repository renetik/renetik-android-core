#!/bin/sh

set -eu

VERSION=${1:-}

if [ "$#" -ne 1 ] || ! printf '%s\n' "$VERSION" | grep -Eq '^[0-9]+\.[0-9]+\.[0-9]+([.-][0-9A-Za-z.-]+)?$'; then
    echo "Usage: $0 <version> (for example: $0 1.0.1)" >&2
    exit 1
fi

SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
cd "$SCRIPT_DIR"

for required_command in git gh; do
    if ! command -v "$required_command" >/dev/null 2>&1; then
        echo "Required command not found: $required_command" >&2
        exit 1
    fi
done

if ! gh auth status >/dev/null 2>&1; then
    echo "GitHub CLI is not authenticated. Run: gh auth login" >&2
    exit 1
fi

if [ ! -x ./gradlew ]; then
    echo "Gradle wrapper is not executable: ./gradlew" >&2
    exit 1
fi

BRANCH=$(git branch --show-current)
if [ "$BRANCH" != "master" ]; then
    echo "Release must run from master; current branch is $BRANCH" >&2
    exit 1
fi

if [ -n "$(git status --porcelain)" ]; then
    echo "Working tree is not clean. Commit or stash changes before releasing." >&2
    git status --short
    exit 1
fi

if git rev-parse --verify --quiet "refs/tags/$VERSION" >/dev/null; then
    echo "Tag already exists locally: $VERSION" >&2
    exit 1
fi

echo "Fetching origin/master and tags..."
git fetch origin master --tags

if git ls-remote --exit-code --tags origin "refs/tags/$VERSION" >/dev/null 2>&1; then
    echo "Tag already exists on origin: $VERSION" >&2
    exit 1
fi

if ! git merge-base --is-ancestor origin/master HEAD; then
    echo "Local master is behind or has diverged from origin/master." >&2
    exit 1
fi

echo "Building and validating Maven publications $VERSION..."
./gradlew clean build publishToMavenLocal --no-daemon \
    -Pgroup=com.github.renetik.renetik-android-core \
    -Pversion="$VERSION"

echo "Pushing master..."
git push origin master

echo "Creating GitHub release $VERSION..."
gh release create "$VERSION" \
    --repo renetik/renetik-android-core \
    --target master \
    --title "$VERSION" \
    --generate-notes

echo "Release created: https://github.com/renetik/renetik-android-core/releases/tag/$VERSION"
echo "JitPack: https://jitpack.io/#renetik/renetik-android-core/$VERSION"
