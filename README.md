# Kit

A suite of modern, highly decoupled Gradle convention plugins designed to make your life as easy as possible.

This is the successor of [DGT](https://github.com/Deftu/Gradle-Toolkit)'s non-Minecraft plugins, and is built with a focus on modularity, maintainability, and ease of use.

---

[![Discord Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/social/discord-singular_64h.png)](https://s.deftu.dev/discord)
[![Ko-Fi Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/donate/kofi-singular_64h.png)](https://s.deftu.dev/kofi)

---

## Architectural Philosophy

This toolkit was engineered from the ground up to comply with modern Gradle's best practices, with a strong emphasis on modularity and maintainability.
Each plugin is designed to be as decoupled as possible, allowing you to pick and choose the functionality you need without unnecessary bloat.

## Plugin Ecosystem

Kit is composed of highly modular plugins. You only apply what you need.

| Plugin ID | Description |
| :--- | :--- |
| [`dev.deftu.kit.core`](./core) | The foundational backbone. |
| [`dev.deftu.kit.repositories`](./repositories) | **[Settings Plugin]** Centralizes dependency resolution and safely injects standard Maven repositories globally. |
| [`dev.deftu.kit.conventions.java`](./conventions-java) | Standardizes Java compilation and UTF-8 encoding. |
| [`dev.deftu.kit.conventions.kotlin-jvm`](./conventions-kotlin-jvm) | Seamlessly synchronizes Kotlin bytecode targets to the underlying Java toolchain. |
| [`dev.deftu.kit.conventions.kotlin-multiplatform`](./conventions-kotlin-multiplatform) | A property-driven KMP architecture that banishes boilerplate and intelligently syncs with the JVM toolchain. |
| [`dev.deftu.kit.maven-releases`](./maven-releases) | Decoupled, KMP-safe Maven publishing with built-in in-memory PGP signing and smart artifact naming. |
| [`dev.deftu.kit.github-releases`](./github-releases) | **TODO** A plugin to automate GitHub releases. |

---

[![BisectHosting](https://www.bisecthosting.com/partners/custom-banners/8fb6621b-811a-473b-9087-c8c42b50e74c.png)](https://bisecthosting.com/deftu)

---

**This project is licensed under [LGPL-3.0][lgpl3].**\
**&copy; 2025 Deftu**

[lgpl3]: https://www.gnu.org/licenses/lgpl-3.0.en.html