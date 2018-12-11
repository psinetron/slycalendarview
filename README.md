# Sly Calendar View

[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![](https://jitpack.io/v/psinetron/slycalendarview.svg)](https://jitpack.io/#psinetron/slycalendarview)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SlyCalendarView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7354)


A calendar that allows you to select both a single date and a period.
Calendar allows you to change colors programmatically without reference to the theme.

<img src="/images/sample.png" alt="Demo Screen Capture" width="300px" />

## Installation

Step 1. Add the JitPack repository to your build file

```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Step 2. Add the dependency

```groovy
dependencies {
  implementation 'com.github.psinetron:slycalendarview:${version}'
}
```


## Usage

1. Add `MaterialCalendarView` into your layouts or view hierarchy.

```xml
<ru.slybeaver.slycalendarview.SlyCalendarView
        android:id="@+id/slyCalendarView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />
```

Or show calendar as dialog:
```java
new SlyCalendarDialog()
        .setSingle(false)
        .setCallback(listener)
        .show(getSupportFragmentManager(), "TAG_SLYCALENDAR");
```

2. Set a `SlyCalendarDialog.Callback` when you need it
```java 
public class MainActivity extends AppCompatActivity implements SlyCalendarDialog.Callback {
...

@Override
    public void onCancelled() {
        //Nothing
    }

    @Override
    public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
        if (firstDate != null) {
            if (secondDate == null) {
                firstDate.set(Calendar.HOUR_OF_DAY, hours);
                firstDate.set(Calendar.MINUTE, minutes);
                Toast.makeText(
                        this,
                        new SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault()).format(firstDate.getTime()),
                        Toast.LENGTH_LONG

                ).show();
            } else {
                Toast.makeText(
                        this,
                        getString(
                                R.string.period,
                                new SimpleDateFormat(getString(R.string.dateFormat), Locale.getDefault()).format(firstDate.getTime()),
                                new SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault()).format(secondDate.getTime())
                        ),
                        Toast.LENGTH_LONG

                ).show();
            }
        }
    }

}
```

Or 
```java
SlyCalendarDialog.Callback callback = new SlyCalendarDialog.Callback() {
    @Override
    public void onCancelled() {
        
    }

    @Override
    public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {

    }
};
```

## Customization
Set colors:
```xml
<ru.slybeaver.slycalendarview.SlyCalendarView
        android:id="@+id/slyCalendarView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:backgroundColor="#000000"
        app:headerColor="#ff0000"
        app:firstMonday="true"
        app:textColor="#00ff00"
        app:selectedColor="#0000ff"
        />
```
or 
```java
new SlyCalendarDialog()
        .setSingle(false)
        .setCallback(listener)
        .setBackgroundColor(Color.parseColor("#ff0000"))
        .setSelectedTextColor(Color.parseColor("#ffff00"))
        .setSelectedColor(Color.parseColor("#0000ff"))
        .show(getSupportFragmentManager(), "TAG_SLYCALENDAR");
```

Parameters:
```xml
<attr name="backgroundColor" format="color"/>
        <attr name="headerColor" format="color"/>
        <attr name="headerTextColor" format="color"/>
        <attr name="textColor" format="color"/>
        <attr name="selectedColor" format="color"/>
        <attr name="selectedTextColor" format="color"/>
        <attr name="firstMonday" format="boolean"/>
```        
