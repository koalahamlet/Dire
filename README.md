# Dire
A library for a dead-simple single Activity model of modern Android application structure using Fragments

Dire handles many common use cases for an app's structure in an effort to make app development as fast and fun as possible.  If your use case does not fit with Dire's assumptions, Dire's logic is easy enough to extend for other purposes, too.


# Get Started
Create an Activity that extends AbsDireStandardActivity, register it in AndroidManifest.xml, and set its theme to an AppCompat (or descendant) theme.
For every screen in your app, use launchFragment() with an extension of AbsDireFragment.  Make sure to setArguments(@Nullable Bundle bundle, @NonNull CharSequence title) on the Fragment.  This allows the Toolbar's title to be properly set deoending on the current screen.
Check out DaftActivity for an example of a simple setup.


# Navigation Drawer
Simply extend AbsDireDrawerActivity and follow the above steps.
Many common navigation drawer patterns are automatically handled in Dire.  Forget worrying about the status bar colors and copying and pasting a ScrimFramgeLayout; let Dire handle those tedious tasks.
Check out PunkActivity for an example of a simple setup.
