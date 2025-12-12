package org.example.project.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberVisualTransformation: VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return if (text.text.isEmpty()) {
            TransformedText(
                text = AnnotatedString(
                    text = "+7 (000)-000-0000",
                    spanStyles = listOf(
                        AnnotatedString.Range(
                            SpanStyle(color = Color.Companion.LightGray),
                            start = 4,
                            end = 17
                        ),
                    )
                ),
                offsetMapping = object : OffsetMapping {
                    override fun originalToTransformed(offset: Int): Int {
                        return 4
                    }

                    override fun transformedToOriginal(offset: Int): Int {
                        return 0
                    }
                }
            )
        } else {
            when (text.text.length) {
                1 -> {
                    TransformedText(
                        text = AnnotatedString(
                            text = "+7 (${text.text}00)-000-0000",
                            spanStyles = listOf(
                                AnnotatedString.Range(
                                    SpanStyle(color = Color.Companion.LightGray),
                                    start = 5,
                                    end = 17
                                ),
                            )
                        ),
                        offsetMapping = object : OffsetMapping {
                            override fun originalToTransformed(offset: Int): Int {
                                return offset + 4
                            }

                            override fun transformedToOriginal(offset: Int): Int {
                                return 1
                            }
                        }
                    )
                }

                2 -> {
                    TransformedText(
                        text = AnnotatedString(
                            text = "+7 (${text.text}0)-000-0000",
                            spanStyles = listOf(
                                AnnotatedString.Range(
                                    SpanStyle(color = Color.Companion.LightGray),
                                    start = 6,
                                    end = 17
                                ),
                            )
                        ),
                        offsetMapping = object : OffsetMapping {
                            override fun originalToTransformed(offset: Int): Int {
                                return offset + 4
                            }

                            override fun transformedToOriginal(offset: Int): Int {
                                return text.text.length
                            }
                        }
                    )
                }

                3 -> {
                    TransformedText(
                        text = AnnotatedString(
                            text = "+7 (${text.text})-000-0000",
                            spanStyles = listOf(
                                AnnotatedString.Range(
                                    SpanStyle(color = Color.Companion.LightGray),
                                    start = 8,
                                    end = 17
                                ),
                            )
                        ),
                        offsetMapping = object : OffsetMapping {
                            override fun originalToTransformed(offset: Int): Int {
                                return offset + 5
                            }

                            override fun transformedToOriginal(offset: Int): Int {
                                return text.text.length
                            }
                        }
                    )
                }

                4 -> {
                    TransformedText(
                        text = AnnotatedString(
                            text = "+7 (${text.text.substring(0..2)})-${text.text.substring(3)}00-0000",
                            spanStyles = listOf(
                                AnnotatedString.Range(
                                    SpanStyle(color = Color.Companion.LightGray),
                                    start = 10,
                                    end = 17
                                ),
                            )
                        ),
                        offsetMapping = object : OffsetMapping {
                            override fun originalToTransformed(offset: Int): Int {
                                return offset + 6
                            }

                            override fun transformedToOriginal(offset: Int): Int {
                                return text.text.length
                            }
                        }
                    )
                }

                5 -> {
                    TransformedText(
                        text = AnnotatedString(
                            text = "+7 (${text.text.substring(0..2)})-${text.text.substring(3)}0-0000",
                            spanStyles = listOf(
                                AnnotatedString.Range(
                                    SpanStyle(color = Color.Companion.LightGray),
                                    start = 11,
                                    end = 17
                                ),
                            )
                        ),
                        offsetMapping = object : OffsetMapping {
                            override fun originalToTransformed(offset: Int): Int {
                                return offset + 6
                            }

                            override fun transformedToOriginal(offset: Int): Int {
                                return text.text.length
                            }
                        }
                    )
                }

                6 -> {
                    TransformedText(
                        text = AnnotatedString(
                            text = "+7 (${text.text.substring(0..2)})-${text.text.substring(3)}-0000",
                            spanStyles = listOf(
                                AnnotatedString.Range(
                                    SpanStyle(color = Color.Companion.LightGray),
                                    start = 12,
                                    end = 17
                                ),
                            )
                        ),
                        offsetMapping = object : OffsetMapping {
                            override fun originalToTransformed(offset: Int): Int {
                                return offset + 6
                            }

                            override fun transformedToOriginal(offset: Int): Int {
                                return text.text.length
                            }
                        }
                    )
                }

                7 -> {
                    TransformedText(
                        text = AnnotatedString(
                            text = "+7 (${text.text.substring(0..2)})-${text.text.substring(3..5)}-${
                                text.text.substring(
                                    6
                                )
                            }000",
                            spanStyles = listOf(
                                AnnotatedString.Range(
                                    SpanStyle(color = Color.Companion.LightGray),
                                    start = 14,
                                    end = 17
                                ),
                            )
                        ),
                        offsetMapping = object : OffsetMapping {
                            override fun originalToTransformed(offset: Int): Int {
                                return offset + 7
                            }

                            override fun transformedToOriginal(offset: Int): Int {
                                return text.text.length
                            }
                        }
                    )
                }

                8 -> {
                    TransformedText(
                        text = AnnotatedString(
                            text = "+7 (${text.text.substring(0..2)})-${text.text.substring(3..5)}-${
                                text.text.substring(
                                    6
                                )
                            }00",
                            spanStyles = listOf(
                                AnnotatedString.Range(
                                    SpanStyle(color = Color.Companion.LightGray),
                                    start = 15,
                                    end = 17
                                ),
                            )
                        ),
                        offsetMapping = object : OffsetMapping {
                            override fun originalToTransformed(offset: Int): Int {
                                return offset + 7
                            }

                            override fun transformedToOriginal(offset: Int): Int {
                                return text.text.length
                            }
                        }
                    )
                }

                9 -> {
                    TransformedText(
                        text = AnnotatedString(
                            text = "+7 (${text.text.substring(0..2)})-${text.text.substring(3..5)}-${
                                text.text.substring(
                                    6
                                )
                            }0",
                            spanStyles = listOf(
                                AnnotatedString.Range(
                                    SpanStyle(color = Color.Companion.LightGray),
                                    start = 16,
                                    end = 17
                                ),
                            )
                        ),
                        offsetMapping = object : OffsetMapping {
                            override fun originalToTransformed(offset: Int): Int {
                                return offset + 7
                            }

                            override fun transformedToOriginal(offset: Int): Int {
                                return text.text.length
                            }
                        }
                    )
                }

                10 -> {
                    TransformedText(
                        text = AnnotatedString(
                            text = "+7 (${text.text.substring(0..2)})-${text.text.substring(3..5)}-${
                                text.text.substring(
                                    6
                                )
                            }",
                        ),
                        offsetMapping = object : OffsetMapping {
                            override fun originalToTransformed(offset: Int): Int {
                                return offset + 7
                            }

                            override fun transformedToOriginal(offset: Int): Int {
                                return text.text.length
                            }
                        }
                    )
                }

                else -> {
                    TransformedText(
                        text = AnnotatedString(
                            text = "+7 (${text.text}00)-000-0000",
                            spanStyles = listOf(
                                AnnotatedString.Range(
                                    SpanStyle(color = Color.Companion.LightGray),
                                    start = 2,
                                    end = 14
                                ),
                            )
                        ),
                        offsetMapping = object : OffsetMapping {
                            override fun originalToTransformed(offset: Int): Int {
                                return 0
                            }

                            override fun transformedToOriginal(offset: Int): Int {
                                return 0
                            }
                        }
                    )
                }
            }
        }
    }
}