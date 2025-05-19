# SurviveGame- Reverse Engineering Project

**GitHub Repository:**  
https://github.com/NitzanLevi8/SurviveGame-Reverse-Engineering-Project

---

## City Result

**ID used:** 123456789  
**City received:** Michigan  
**Game result:** Survived  
---

## Project Overview

This project is based on reverse engineering an Android APK file.  
The goal was to understand the game logic, identify bugs, and rebuild the app with working functionality.

---

## What the App Does

1. The user enters an ID number.
2. Based on the last digit of the ID, the app selects a city from a list.
3. A game starts where the player must repeat a sequence of directions.
4. The sequence is created based on the ID digits.
5. If the player repeats the pattern correctly, they survive. Otherwise, they fail.

---

## Key Fixes and How I Solved Them

### 1. **App crash on game start (NullPointerException)**
- Problem: The game screen expected values with keys `"id"` and `"city"`, but they were sent using `"EXTRA_ID"` and `"EXTRA_STATE"`.
- Fix: Used consistent keys `"id"` and `"city"` in both activities.

### 2. **No instruction about which arrows to press**
- Problem: The user had no clue what the pattern was.
- Fix: Added a visible TextView showing the correct direction sequence before playing.

### 3. **Wrong city selected**
- Problem: The app used the 8th character of the ID directly as an index.
- Fix: Adjusted the logic to use `(lastDigit - 1 + cities.length) % cities.length` to always get a valid city.

### 4. **Game doesn’t return to menu after win/fail**
- Problem: After showing the toast message, the game stayed open.
- Fix: After showing the result, I added a short delay and then returned to the main menu.

---

## Notes

- All design and game logic was recreated from analyzing smali files and the original APK.
- Bugs were not always visible at first, I tested edge cases.
---

Video demo link: ()
