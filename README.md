# Kvasir

Kvasir (/kwɑ'zer/) is an (unofficial) Intellij IDEa plugin adding support for [Typst](https://typst.app/docs) language.

![screenshot.png](screenshot.png)
![preview.png](preview.png)

## Features

- Customizable syntax highlight
- Instant preview (Uses `typst watch` for now)
- Compilation errors display
- Theme-aware preview (background and foreground colors are accessible via `sys.inputs.kvasir-preview-background` and `-foreground` respectively, as a rgb hex string)

The plugin is in the beta stage, a lot of features are yet to come. 
See _Roadmap_ for the information about planned features, 
and _Nearest plans_ for those which are currently in development. 
Feel free to open issues and pull requests.

## Installation

The plugin can be manually installed from disk with [archive](distributions/Kvasir-0.2.0-signed.zip), 
or from JetBrains Marketplace by name.

## Roadmap
(Before opening a feature request make sure it's not already planned)

Listed by group, not by priority (see _Nearest plans_ for those)

- Highlighting
  - [X] Basic Typst formatting
  - [X] Rainbow brackets
  - [ ] Math support
  - [ ] Error recovery (one error shouldn't bust highlighting for the rest of the file)
  - [ ] Language injections: highlight raw code due to its actual language
- Preview and Compiler errors
  - [X] Basic (typst watch)
  - [ ] Integrate tinymist
- Formatter
  - [ ] Sketch formatter (integrate typstfmt or typstyle)
  - [ ] Intellij-based, configurable formatter
- Indexing
  - [ ] Go to definition, renaming, find usages
  - [ ] Hover tips
  - [ ] Inlay hints
  - [ ] Documentation pop-ups
- Scope recognition
  - [ ] Folding ranges
  - [ ] Highlighting current scope
  - [ ] Colored guides
- Code Actions
  - [ ] Commenter
  - [ ] "Surround with" (shortcuts for making text italic, bold, etc)
  - [ ] Introduce variable
  - [ ] Pattern recognition (how am I gonna do that? omg)
- Project-level support
  - [ ] Gradle plugin
  - [ ] IDEa project template
  - [ ] Typst project template

## Nearest plans

- Sketch formatter
- More settings
- Error recovery
- Go to definition, renaming, find usages

## Known bugs

- Shorthands inside headings aren't highlighted properly.
- If `page.fill` is not `set` explicitly, the picture may appear transparent. `#set page(fill:white)` (or another color) helps.
- Errors in the `File Errors` are shown in the order of how they are located in the document, not in order of the stacktrace. I'm not sure if it's even possible, but I'm working on it.

## Contacts

Telegram: @LDemetrios
Mail: ldemetrios@yandex.ru
