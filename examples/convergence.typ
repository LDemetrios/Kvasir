#let cos-fixed(init) = context {
   let queried = query(<label>)
   let value = if queried.len() == 0 { init } else { queried.at(0).value }
   [#metadata(calc.cos(value)) <label> #calc.cos(value)]
}

#cos-fixed(1)

