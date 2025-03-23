## Changelog

## 0.3.0

- Typst 0.13 is supported.
- Fixed annoying freezing of various components.
- Three file extensions are now supported: `typ` for markup, `typc` for code, `typm` for math. All share the same set of features (code highlighting, preview, formatter, code actions, etc).
- Formatter was added (based on [typstyle](https://github.com/Enter-tainer/typstyle)). Settings for it can be found in Settings/Tools/Kvasir.
- Primitive code actions were added (commenter, surround-with).
- Compilation errors are now shown in their trace order, not in location order.
- Preview now works much faster, utilizing native code through JNA.
- Preview now works almost fully with in-memory files, not loading the disk (except for package access).
- `inputs` (kvasir-preview-foreground, kvasir-preview-background) are now not `str`s, but `color`s. Known downside is that they are now set once upon IDE loading, and don't change when theme is changed without reloading. I know how to fix it, but that requires time.
- Syntax highlighting improved, it now mixes styles properly without exponential amount of keys in settings
- Some people complained about rainbowifying. It now can be disabled in plugin settings (Tools/Kvasir).
- Scrolling and zooming speed can now also be customized.

... and that's actually when this changelog was created.

## 0.2.1, 0.2.2 

Compatibility adjustments

## 0.2.0

Preview introduced

## 0.1.0 

First appearance