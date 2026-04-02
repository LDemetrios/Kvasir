#set page(fill: sys.inputs.kvasir-preview-background)
#set text(fill: sys.inputs.kvasir-preview-foreground)

#let getfield = 1
#let getstatic = 2
#let putfield = 3
#let putstatic = 4
#let invokevirtual = 5
#let invokestatic = 6
#let constructor = 8

#let upcall = upcall.with(breakglass: "TEST")

#assert.eq(upcall(invokestatic, "java.lang.Integer#sum:(II)I", 1, 2), 3)

#upcall(invokestatic, "java.lang.System#getProperty:(Ljava/lang/String;)Ljava/lang/String;", "java.version")

//#let lookup = upcall(invokestatic, "java.lang.invoke.MethodHandles#publicLookup:()Ljava/lang/invoke/MethodHandles$Lookup;")
//#upcall(invokestatic, "java.lang.Class#forName:(Ljava/lang/String;)Ljava/lang/Class;")
//#upcall(getstatic, "org.ldemetrios.kvasir.preview.data.BytesClassLoader#INSTANCE:Lorg/ldemetrios/kvasir/preview/data/BytesClassLoader;")

//#let Example =
#let Example = define-class("org.ldemetrios.tyko.manual.jar.Example", read("Example.class", encoding: none))
#Example
#let lookup = lookup-in(Example)
#upcall(breakglass: "TEST", invokestatic, "org.ldemetrios.tyko.manual.jar.Example#add:(SS)I")
