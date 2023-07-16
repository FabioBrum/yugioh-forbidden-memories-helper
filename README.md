# Yu-Gi-Oh! Forbidden Memories Helper
This is a personal app that I started to build after one year of experience on Android programming. I've always enjoyed playing the [*Yu-Gi-Oh! Forbidden Memories*](https://pt.wikipedia.org/wiki/Yu-Gi-Oh!_Forbidden_Memories) game on my Playstation 1 during my childhood. After all these years, I still play it both on my console and my smartphone (with emulators).

But even after more than a decade playing this game I still have the same problem: *there is so much information that I can't keep track of it!* There are so many possible fusions, and each character in the game drops different cards, with different odds for each type of victory, that my mind can't keep up storing all of it :sweat_smile: ...

Therefore, I decided to develop an Android App that would solve this problem for me, which is where I got the *Yu-Gi-Oh! Forbidden Memories Helper* idea from. I also started this project, of course, to use it as part of my portfolio and to show my progress from my previous project, which I started last year. But from the start I wanted to develop something that was personal and useful to me, and I'm happy to say that I created something that I really use in my everyday life (and that you can too, if you go through the same problem playing this game :wink:) 

## Architecture
The YuGiOh Forbidden Memories Helper app was built using MVVM architecture with Clean Architecture principals, based also on android project [Now in Android](https://github.com/android/nowinandroid). 

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
| :core:network | All code related to communication outside of the app. This module contains the code used to download information and images of cards and characters. Currently, is not being used in the app (the reason which will be explained in following topics) but the code is still if future use is necessary | `OnlineCardsRepositoryImpl`, `OnlineCharactersRepositoryImpl` |
| :core:database | All code related to the app databases. This includes the databases themselves, data entities, mappers and DAOs | `CardDao`, `CardEntity`, `Database`, `CardMappers` |

## Navigation
The navigation of the app was built using [Jetpack's Navigation](https://developer.android.com/guide/navigation), following principles on multi-modules [documentation](https://developer.android.com/guide/navigation/navigation-multi-module). On the following image we have a graph representing the entire app's navigation:

![Navigation](https://github.com/FabioBrum/yugioh-forbidden-memories-helper/assets/49159226/75bb1d94-2a32-4bec-837a-30424be46112)

This `BottomNavigationView` enables the user to enter any of the three flows at any screen in the app, keeping the their current navigation stack. Here is how this view is presented on the app:

![BottomNavigationView](https://github.com/FabioBrum/yugioh-forbidden-memories-helper/assets/49159226/66329dd3-8353-4b01-9479-a3e4788e963b)

## Database
I decided to divide the app in two different databases: `Database` and `UserDatabase`. This decision was based on the idea that all immutable and mutable data should be on separated, in `Database` and `UserDatabase` respectively.

Therefore, the `Database` will store information about `Cards` and `Characters`, since those are not going to change for the reason that the game won't receive any updates in the future. The game has, in total, *722* cards and *33* characters, with each character having three different lists of drop probabilities depending on the winning condition of the player. Therefore, collecting all these information and downloading the cards and character images manually was not an option. To solve this problem, I developed two web scraping scripts (which are still on `:core:network`), using the [JSOUP](https://jsoup.org/) tool, to download everything I needed automatically from pages made by fandoms ([here](https://yugipedia.com/wiki/Yugipedia) and [here](https://yugioh.fandom.com/wiki)). It was able to download all cards and characters information, and most images as well (a small amount of card images wasn't found).

With this information retrieved with the script, I was able to populate both *cards* and *characters* tables and store their images on `:core:database:assests`. After doing so, I exported this database to a `database.db` file. Now, instead of running this script each time the app is opened by the first time (which consumes both time and internet), the app builds `Database` from this file, which is also stored in `:core:database:assests`. This means that this database is immutable, with only reading behaviors.

The `UserDatabase` is mutable, though. Therefore, none of the behaviors above affect it, which means that is unique to each user. Currently, it only stores information about *fusions*, but this might change with the implementation of possible new features in the future.

## Libraries used

`Dependency injection`: [Koin](https://insert-koin.io/docs/quickstart/android/)
`Database`: [Room](https://developer.android.com/training/data-storage/room)
`Navigation`: [Jetpack's Navigation](https://developer.android.com/guide/navigation)
`Webscraping`: [JSOUP](https://jsoup.org/)
`Unit tests` [JUnit4](https://junit.org/junit4/) and [mockK](https://mockk.io/ANDROID.html)