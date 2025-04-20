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

- Material Design 3
- Material You

  - Application Icon and Activies buttons change according to theme

- Transactions

  - Different colors to differentiate Expenses and Incomes

100% offline
privacy respecting
no permissions required

- Data is saved locally and ever leaves your device.

- CRUD - Create Read Update Delete
  - Add transaction / account / category
  - Edit transaction / account / category
  - Delete transaction / account / category
- Colors
  - Implementation of Mateial You
    - Application Icon
    - All icons
  - Different colors to differentiate Expenses and Incomes
- Activities
  - Main activity
    - Navigation bar
    - Tool bar
  - Adding new transaction / account / category
  - Editing new transaction / account / category
  - Statistics
  - Transactions filtered by account / category
- Fragments
  - Transactions page
  - Accounts page
  - Categories page
- Recycler View for all cards
  - Transactions
  - Accounts -> FlexboxLayout
  - Categories -> FlexboxLayout
- Sorting:
  - Transactions by date
  - Accounts and Categories by alphabetical order
- Menus
  - Dropdown menus
  - Navigation bar menu
  - Tool bar menu
- Statistics
  - Mounthly statistics for categories

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
    - <!-- To Do -->
  - Domain Layer
    - **UseCases**
  - Data Layer
    - **Repositories**
    - **DataSources**
- [x] Android architectural principles
  - [**Unidirectional Data Flow**][7]
  - [**Single Source of Truth**][6]
  - [_One-shot_ **CRUD** APIs][8] defined in the Data Layer

[4]: https://en.wikipedia.org/wiki/Plain_old_Java_object
[5]: https://developer.android.com/topic/architecture#recommended-app-arch
[6]: https://developer.android.com/topic/architecture/data-layer#source-of-truth
[7]: https://developer.android.com/topic/architecture#unidirectional-data-flow
[8]: https://developer.android.com/topic/architecture/data-layer#expose-apis

[![class diagram](./docs/class-diagram.svg)](https://raw.githubusercontent.com/jaczanet/Expenses/96513d620ee8923b46ea1cc3e5fb685a0398f8eb/docs/class-diagram.svg "Open raw")
