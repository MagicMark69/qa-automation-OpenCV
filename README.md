# QA Automation Demo

This is a Java-based desktop automation tool leveraging OpenCV for image recognition. Originally developed for game automation, it has since been generalized for broader UI testing and desktop interaction scenarios.

## 🔧 Features
- AI-based image recognition
- Automates clicks, keystrokes, and screen navigation
- Wait-for-element and timeout handling
- Screenshot logging
- [NEW] Robust error handling with tryRun(): Gracefully handle failures without terminating the script. Users are prompted to continue or stop the automation when an error occurs.

## 🧪 Demo Scripts

### `TestTextFile.java`
Automates Notepad to:
1. Create and type content into a `.txt` file  
2. Save the file  
3. Check if the contents are correct  
4. Rename the file  
5. Delete it afterward  

### `TestFormFill.java`
Automates form interaction on [W3Schools HTML Forms](https://www.w3schools.com/Html/html_forms.asp), including:
1. Filling in forms  
2. Selecting radio buttons  
3. Checking and unchecking checkboxes  

## 🖥️ Technologies Used
- Java 21
- OpenCV (via JavaCPP)
- AWT / Robot API
- IntelliJ IDEA

## 🎬 Demo
[watch the full video] (https://youtu.be/5xOYyzx1Db4)

## 🗂️ How to Use

### ▶️ Run as-is:
1. Clone the repository.
2. Set up OpenCV via Maven or JavaCPP.
3. Verify that the images in the `resources/` folder match your screen setup.  
   If not, generate your own by running `Screenshot.java` while on the appropriate screen. Then rename and crop the saved screenshot accordingly.
4. Run either `TestTextFile.java` or `TestFormFill.java`.

### 🛠️ Customize for Your Workflow:
1. Complete Steps 1–2 above.
2. Replace target images by running `Screenshot.java` and capturing relevant screens. Rename and crop as needed.
3. Code your automation workflow using:
   - `Shortcuts.click()` — to simulate clicks using target images.
   - `Functions.waitForTarget()` — to wait for UI elements before proceeding.
   - `Functions.typeString()` - to simulate keyboard presses for typing.



## ⚠️ Disclaimer
This tool is presented for educational and testing purposes only. Do not use it on platforms that prohibit automation.

## 📜 License
MIT License
