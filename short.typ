// This is bad!
#let s = state("x", 1)
#context s.update(s.final() + 1)
#context s.get()

