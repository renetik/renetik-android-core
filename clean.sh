#!/usr/bin/env bash
set -euo pipefail
echo "Clean Script Start"
START_DIR="$(pwd)"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
echo "Cleaning all from:$SCRIPT_DIR"
cd "$SCRIPT_DIR"
find . -type d \( -name '.gradle' -o -name '.kotlin' -o -name 'build' -o -name '.cxx' \) -prune -exec rm -rf {} +
echo "Running gradle clean"
./gradlew clean --no-configuration-cache
cd "$START_DIR"
echo "Clean Script Done"