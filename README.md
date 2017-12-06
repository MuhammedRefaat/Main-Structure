the Main Structure of Android Application, contains:
- Retrofit set as a server callie. 
- ORMLite set as a SQLite database client.
- a MainActivity with NavigationDrawer and a TabLayout with 2 sliding tabs.
- a RecyclerView controller attached to an adapter to display data on any fragment and it is already applied to the both fragments contained in the sliding tab.
- a Controller to download images using "Picasso" Library, in addition to saving them to the SDCard, transforming them to a Circle Images.


-----------------------------------------

Before building on that structure, don't forget to:
1- change app name and package name.
2- go to "AppPreferences.class" in misc package to change the app static preferences regarding server calling URL & Media Storage directory.
