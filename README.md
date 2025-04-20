# Project Work

<!-- General project description -->

Personal expenses tracking application with a minimalistic yet modern design.

Time to find out where the money goes.

## Team members

<!-- Division of work among team members -->

- [Jacopo Zanetti][1]: Focus on Data and Domain Layers, realization of class diagrams and documentation.
- [Enerel Tumurkhuu][2]: Focus on UI Layer (UI Classes and xml files), and UI/UX Design on Figma.
- [Gabriel Concepcion][3]: Focus on UI Layer (UI Classes and xml files), realization of presentation video.

[1]: https://jacza.net/github
[2]: https://github.com/eenerere/
[3]: https://github.com/g-concept999

## Features

<!-- Implemented features -->

- Material Design 3.
- Material You dynamic colors, in icons and UI elements.
- 100% offline, data is saved locally and never leaves your device.
- Privacy respecting, no permissions required.
- Track your transactions details:
  - Date,
  - Amount,
  - Note,
  - Category,
  - Account.
- Follow your account balances, whether starting from zero or an existing balance.
- Monthly statistics based on transaction categories.
- Sensible colors to differentiate expenses and incomes.
- Filter transactions per category or account.
- Sensible presentation of data with built-in sorting logic.

## Compliance with project requirements

- [x] English-only codebase
- [x] 100% Java, support Android 10 or higher
- [x] Make extensive use of Java's rich type system
  - **Abstract** classes
  - **Static** methods
  - **Interfaces**
  - **Generics**
  - **Enums**
- [x] OOP Design Patterns
  - **Singletons**
  - **Inheritance** and **Polymorphism**
  - **SOLID** principles
- [x] Data Structures
  - **ArrayLists** are widely used throughout the code
  - **HashMaps** are used when convenient over ArrayLists
  - Many different [**POJOs**][4] definted according to use case
- [x] Android's [officially][5] recommended app **architecture**
  - UI Layer
    - **Activities** and **Fragments**
    - **FlexBox Layouts** with **Recycler Views**
    - **UI Elements**
      - Navigation Bar
      - Tool Bar
      - Material CardViews
      - Dropdown Menus
      - Date Picker
      - Material Buttons
  - Domain Layer
    - **UseCases**
  - Data Layer
    - **Repositories**
    - **DataSources**
- [x] Android architectural principles
  - [**Unidirectional Data Flow**][7]
  - [**Single Source of Truth**][6]
  - [_One-shot_ **CRUD** APIs][8] exposed by the Data Layer

[4]: https://en.wikipedia.org/wiki/Plain_old_Java_object
[5]: https://developer.android.com/topic/architecture#recommended-app-arch
[6]: https://developer.android.com/topic/architecture/data-layer#source-of-truth
[7]: https://developer.android.com/topic/architecture#unidirectional-data-flow
[8]: https://developer.android.com/topic/architecture/data-layer#expose-apis

### Class Diagram

##### Modifiers Convention

- Abstract : _Italics_
- Static : <ins>Underlined</ins>
- Final : ALL_CAPS

[![class diagram](docs/class-diagram.svg)](https://raw.githubusercontent.com/jaczanet/Expenses/refs/heads/main/docs/class-diagram.svg "Open fullscreen")

Click on the diagram to open it full-screen. Tip: You may zoom out to gain a broad overview.

## Screenshots

| ![][31] | ![][19] | ![][18] | ![][17] |
| ------- | ------- | ------- | ------- |
| ![][15] | ![][13] | ![][14] | ![][16] |
| ![][11] | ![][9]  | ![][10] | ![][12] |

| ![][32] | ![][30] | ![][29] | ![][28] |
| ------- | ------- | ------- | ------- |
| ![][26] | ![][24] | ![][25] | ![][27] |
| ![][22] | ![][20] | ![][21] | ![][23] |

[9]: docs/screenshots/accounts-dropdown-menu-light.png
[10]: docs/screenshots/accounts-edit-light.png
[11]: docs/screenshots/accounts-fragment-light.png
[12]: docs/screenshots/accounts-transactions-view-light.png
[13]: docs/screenshots/categories-dropdown-menu-light.png
[14]: docs/screenshots/categories-edit-light.png
[15]: docs/screenshots/categories-fragment-light.png
[16]: docs/screenshots/categories-transaction-view-light.png
[17]: docs/screenshots/statistics-activity-light.png
[18]: docs/screenshots/transactions-edit-light.png
[19]: docs/screenshots/transactions-fragment-light.png
[20]: docs/screenshots/accounts-dropdown-menu-dark.png
[21]: docs/screenshots/accounts-edit-dark.png
[22]: docs/screenshots/accounts-fragment-dark.png
[23]: docs/screenshots/accounts-transactions-view-dark.png
[24]: docs/screenshots/categories-dropdown-menu-dark.png
[25]: docs/screenshots/categories-edit-dark.png
[26]: docs/screenshots/categories-fragment-dark.png
[27]: docs/screenshots/categories-transaction-view-dark.png
[28]: docs/screenshots/statistics-activity-dark.png
[29]: docs/screenshots/transactions-edit-dark.png
[30]: docs/screenshots/transactions-fragment-dark.png
[31]: docs/screenshots/icon-md3-you-sample-1.png
[32]: docs/screenshots/icon-md3-you-sample-2.png
