###### Charging stations finder

#### Project Specifications
You need to implement an Android application that loads a list of charging stations and
present it on the map. When a user selects any item on the map, show a details view of the
selected item. You can decide how every screen or POI on the map will look like. No specific
design requirements. Apart from that, reload charging stations every 30 seconds and present
them on the map view.
API:
Charging stations could be loaded using the following API:
https://openchargemap.org/site/develop/api
Please use the following parameters for your request:
Initial position = latitude: 52.526 longitude: 13.415
API key =key
Map screen:
On the map screen show all the POIs provided by the request. List of POIs has to be updated
every 30 seconds. When user press on the POI, show details screen. Using a user/device
location is not needed yet.
Details screen:
Details screen shows additional information about the selected POI. On the details screen user
should see:
• Title
• Charging station address
• Number of charging points
Additional requirements:
Application has to be written in Kotlin and covered by unit tests.
It would be nice if you use reactive approach with Kotlin coroutines.


#### Tech stack used

1. MVVM
2. Hilt for DI
3. Retrofit
4. Gson
5. State Flow
6. Coroutines for Concurrent work
7. Domain driven design principle
8. Kotlin
9. View binding
10. Mockito 


