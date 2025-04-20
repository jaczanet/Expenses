# Project Work

<!-- General project description -->

Personal expenses tracking application with a minimalistic yet modern design.

Time to find out where the money goes.

## Team members

<!-- Division of work among team members -->

- [Jacopo Zanetti][1]: Focus on Data and Domain Layers, realization of Class diagrams
- [Enerel Tumurkhuu][2]: Focus on UI Layer (UI Classes and xml files), and UI/UX Design on Figma
- [Gabriel Concepcion][3]: Focus on UI Layer (UI Classes and xml files), realization of presentation video

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

- [x] English-only codebase.
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
    - **UI Elements** (navigation bar, tool bar, material cardviews, dropdown menus)
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

[![class diagram](./docs/class-diagram.svg)](https://raw.githubusercontent.com/jaczanet/Expenses/96513d620ee8923b46ea1cc3e5fb685a0398f8eb/docs/class-diagram.svg "Open raw")
