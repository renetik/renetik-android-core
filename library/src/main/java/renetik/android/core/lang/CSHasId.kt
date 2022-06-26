package renetik.android.core.lang

interface CSHasId {
	companion object

	val id: String
}

fun CSId(id: String): CSHasId = object : CSHasId {
	override val id = id
}