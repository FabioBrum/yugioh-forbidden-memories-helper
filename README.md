# YuGiOh Forbidden Memories Helper

## Architecture
The YuGiOh Forbidden Memories Helper app was built using MVVM architecture principals, based also on android project [Now in Android](https://github.com/android/nowinandroid). 

## Modularization
The app has two main module layers: `Feature` and `Core`;

`Feature`: It contains all modules related to UI in the app. In this project we have four feature modules: `:feature:cards`, `:feature:characters`, `:feature:fusion` and `:feature:designsystem`. Since these modules are only responsible for UI, it doesn't implement any modules related to the data layer, which guarantees that all UI code is agnostic to data entities and technologies. This means the UI layer only uses domain entities, so *all* data has to go through a mapping before it reaches this layer;
`Core`: All modules related to domain and data layer are contained in this module layer. This project contain three core modules: `:core:domain`, `:core:network` and `:core:database`. Even though the domain layer is contained in this package together with the data layer, it doesn't have any dependencies to these modules, respecting the agnosticism of any layer required for a correct implementation of a domain layer. 

This graph represents the current app modularization, with all dependencies between them represented by the arrows:
![Diagrama de dependências](https://github.com/FabioBrum/yugioh-forbidden-memories-helper/assets/49159226/b41497e7-d588-431c-aeab-af927793218b)

Here is a better description of each module:
| Module name | Description | Example of classes |
| ----------- | ----------- | ------------------ |
| :feature:cards | All UI classes and designs related to card navigation flow of the app | `MainCardsFragment`, `MainCardsViewModel` |
| :feature:fusion | All UI classes and designs related to fusion navigation flow of the app | `CreateFusionFragment`, `CreateFusionViewModel` |
| :feature:characters | All UI classes and designs related to characters navigation flow of the app | `CharacterDetailsFragment`, `CharacterDetailsViewModel` |
| :feature:designsystem | All UI classes and designs that are common to multiple feature modules | `CardDetailDialog`, `CardListAdapter` |
| :core:domain | All classes related to business logic in the app. This includes: domain entities, repository contracts and use cases | `FusionRepository`, `Fusion`, `FilterCardsUseCase` |
| :core:network | All code related to communication outside of the app. This module contains the code used to download information and images of cards and characters. Currently, is not being used in the app, like already explained in topics above, but the code is still if future use is necessary | `OnlineCardsRepositoryImpl`, `OnlineCharactersRepositoryImpl` |
| :core:database | All code related to the app databases. This includes the databases themselves, data entities, mappers and DAOs | `CardDao`, `CardEntity`, `Database`, `CardMappers` |

## Navigation
![Navigation](https://github.com/FabioBrum/yugioh-forbidden-memories-helper/assets/49159226/75bb1d94-2a32-4bec-837a-30424be46112)
