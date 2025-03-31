package org.ldemetrios.tyko.ffi

@RequiresOptIn(
    "This is an entity for FFI. If used carelessly, it may trigger panic, ruthlessly shutting down the whole process.",
    RequiresOptIn.Level.ERROR
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class TyKoFFIEntity

