# 🏫 Silver Oak School

**Silver Oak School** is a comprehensive Android application designed to digitize and simplify the core functionalities of a modern school system. It provides dedicated modules for **students**, **teachers**, and **registrars**, each with role-specific features that streamline tasks such as scheduling, grade management, assignment submissions, and academic planning.

Built with a strong focus on user experience, the app integrates essential Android components, responsive layouts, and a clean design system – all powered by a robust backend built using **PHP web services** and **MySQL**.


---

## ✨ Key Features

- 📌 **GPA Calculator**
- 📝 **To-Do List / Study Planner**
- 🌙 **Dark Mode Toggle** (Switch + centralized styles)

- 🧑‍🏫 **Teacher Module**
  - View schedule
  - Publish grades
  - Send assignments
  - View assignment submissions

- 🧑‍🎓 **Student Module**
  - View schedule
  - View grades
  - View and submit assignments
  - Receive assignment notifications

- 📋 **Registrar Module**
  - Manage student/teacher details
  - Build schedules
  - Add subjects

- 🏠 **Main Pages (Before Login)**
  - 🚀 Splash Screen
  - 🔐 Login Page
  - 🏫 About School
  - 📬 Contact Us
  - 📰 Newsletter Subscription
  - 📅 Mock Event Calendar
 ---

## 🔧 Technical Highlights

This application demonstrates solid Android development practices, including:

- ✅ Persistent storage with `SharedPreferences` for saving user preferences and form data — enabling smoother navigation and a more polished user experience.
- ✅ Responsive layouts using **ConstraintLayout**, **LinearLayout**, and **RelativeLayout**.
- ✅ Efficient data display using **RecyclerView** and **ListView**.
- ✅ RESTful communication with **PHP-based Web Services** and a **MySQL** backend using **Volley**.
- ✅ Proper lifecycle handling and orientation management.
- ✅ Centralized styling via `styles.xml` and UI strings via `strings.xml`.
- 
---
### 🔐 Login Page

This is the main entry point of the app. Users can log in using their email and password. A **"Remember Me"** checkbox is implemented using `SharedPreferences` to persist login info for convenience.  
The page also offers quick access to public pages: **Contact Us**, **News**, **Events**, and **About Us**.
<p align="center">
  <img src="https://github.com/user-attachments/assets/ea3f8ed9-f5a2-4e0b-85c3-8e61769659ae" alt="Login Page" width="250"/>
</p>

---

### 🧑‍🏫 Teacher Main Page

This screen provides teachers with an overview of the subjects they are assigned to. Each subject is displayed in a dedicated dark-themed card containing the following options:

- 📨 **Send Assignment** – Allows teachers to push new assignments to their students.
- 📥 **View Assignments/Replies** – View submissions and feedback from students.


The interface also includes:

- 📌 A **bottom navigation bar** for quick access to:
  - 🏠 **Dashboard**
  - 📆 **Schedule**
  - 📝 **Marks**

 The page is styled using **Dark Mode**, with clear contrast and elegant layout blocks , designed for maximum usability, ensuring that teachers can manage their academic responsibilities efficiently and intuitively.
<p align="center">
  <img src="https://github.com/user-attachments/assets/d4b4bb13-6647-42f7-8e59-80a1c34f024f" width="220"/> &nbsp; 
  <img src="https://github.com/user-attachments/assets/e70e60a3-33c8-437d-b63d-c012d3863d58" width="220"/>
</p>

---

### 🗓️ Teacher Schedule Page

This screen displays the teacher’s weekly schedule with a clear layout and segmented day navigation.

- 📅 The top of the screen provides a horizontal day selector (Sun to Thu) for browsing each day's schedule.
- 🕗 Each time slot lists:
  - The subject name 
  - The assigned classroom 
- 🎨 The design uses dark-themed cards for visual contrast, enhancing readability.


<p align="center">
  <img src="https://github.com/user-attachments/assets/fdd46b55-5525-437a-a7e1-0b613d3491f6" alt="Teacher Schedule Page" width="250"/>
</p>

---

### 📝 Upload Marks Page

This screen allows teachers to upload and manage students’ exam marks efficiently through a streamlined and responsive UI.

#### Key Features:
- 🎓 **Subject Selection**: Teachers can select one of their assigned subjects from a dropdown list.
- 🧾 **Exam Details**: Input fields for:
  - **Exam Type** (e.g., Midterm, Final)
  - **Full Mark**
- 🧑‍🎓 **Dynamic Student List**:
  - Once the subject is selected, the system automatically loads the list of enrolled students.
  - Teachers can enter marks for each student directly.

#### 🔄 Edit Existing Marks:
- Teachers can search and filter existing student records in real-time.
- The search bar supports **live filtering** — as you type, matching students are displayed instantly.


<p align="center">
  <img src="https://github.com/user-attachments/assets/29a684a9-78e6-47ac-b9e1-4fca52104601" alt="Upload Marks Page" width="250"/>
</p>

---

### 📤 Send Assignment Page

This screen allows teachers to create and submit new assignments to students with rich options and intuitive input fields.

#### Key Features:
- 📝 **Assignment Title & Description**: Teachers can define the task clearly.
- 📎 **File Attachments**: Option to attach relevant files (PDFs, images, docs, etc.) to the assignment.
- 📅 **Due Date Picker**:
  - Uses a native **Date Picker Dialog** to let teachers select the due date easily.
  - The selected due date is displayed clearly below the button.
- 🚀 **Submission**:
  - Once all fields are filled, teachers can submit the assignment .


<p align="center">
  <img src="https://github.com/user-attachments/assets/1d2db391-3f81-46c2-9743-847921fabc09" alt="Send Assignment Page" width="250"/>
</p>

---

### 📚 View Assignments Page

This screen displays all assignments that have been posted by the teacher for a specific subject.

#### Key Features:
- 📌 Each assignment is shown as a **dark-themed card** containing:
  - 🧾 **Title**
  - 📘 **Subject Name**
  - 📅 **Due Date**
  - 📄 **Description** of the assignment
  - 🔗 **Attached File Link** (clickable if file is available)
- 📥 **View Replies** button:
  - Allows the teacher to view all student responses to that particular assignment.


<p align="center">
  <img src="https://github.com/user-attachments/assets/66712a74-1d73-4cc0-8321-360beab188c5" alt="View Assignments Page" width="250"/>
</p>

---
### 📥 Assignment Replies Page

This screen lists all student submissions for a specific assignment in a clean and organized card-based view.

#### Key Features:
- 👤 Each student reply card displays:
  - 🧑 **Student Name**
  - 🗓️ **Submission Timestamp**
  - ✉️ **Text Response** (if any)
  - 🔗 **Attached File** (if available)

This screen ensures that teachers can easily review all replies, track submission times, and verify content or attached files.

<p align="center">
  <img src="https://github.com/user-attachments/assets/7d0377b5-66f0-4b20-be93-0773f02dc55b" alt="Assignment Replies Page" width="250"/>
</p>

----

### 🧑‍🎓 Student Main Page

This is the main dashboard for students after logging in. It provides quick access to essential tools and information, ensuring a smooth academic experience.

#### Key Features:
- 🌓 **Dark Mode Toggle**: Switch between light and dark themes using a simple toggle. The state is saved using `SharedPreferences` for consistency across sessions.
- 📚 **Subjects List**: Displays all subjects assigned to the logged-in student.
- 📤 **Submit Assignment**: Navigate to the assignment submission page.
- 📊 **GPA Calculator**: Helps students calculate their current GPA based on subject grades.
- ✅ **To-Do List**: Access a personalized planner for tracking tasks and study goals.

- Bottom navigation bar provides access to:
  - 🏠 **Dashboard**
  - 📆 **Schedule**
  - 📝 **Marks**

<p align="center">
  <img src="https://github.com/user-attachments/assets/863ab924-7b44-4385-8363-1108e9c9114d" alt="Student Main Page" width="250"/>
</p>

---

### ✅ To-Do List Page

This screen provides students with a personal study planner to manage their academic tasks and track progress efficiently.

#### Key Features:
- ➕ **Add Task**: Students can enter new tasks and add them to their list.
- ✅ **Tap to Complete**:
  - When a task is completed, tapping on it will instantly remove it from the list — providing a smooth and intuitive experience.
- 💾 **Persistent Storage**:
  - Tasks are stored locally using **Room Database**, ensuring data is retained even when the app is closed or restarted.


<p align="center">
  <img src="https://github.com/user-attachments/assets/f1fea352-fb0d-4d1f-844e-78b0969455eb" alt="To-Do List Page" width="250"/>
</p>

---

### 🎓 GPA Calculator Page

This screen allows students to calculate their Grade Point Average (GPA) based on the grades they input for various subjects.

#### Key Features:
- ➕ **Add Subject and Mark**:
  - Students can enter the name of each subject and their corresponding mark.
  - Clicking **"Add Subject"** adds the entry to the list dynamically.
- 📊 **GPA Calculation**:
  - The **"Calculate GPA"** button instantly computes the average mark based on all added subjects.
  - The result is shown clearly at the bottom of the screen.


<p align="center">
  <img src="https://github.com/user-attachments/assets/b5566ec5-8210-4bb5-8779-32cd7d0fd54d" alt="GPA Calculator Page" width="250"/>
</p>

---
### 📨 Submit Assignments Page

This screen displays all the assignments assigned to the student and allows them to submit their responses with optional file attachments.

#### Key Features:
- 📋 **Assignment Overview**:
  - Each assignment card shows:
    - 🧾 Title
    - 📘 Subject
    - 📅 Due Date
- 📤 **Submit Button**:
  - Tapping the **Submit** button opens the submission screen where the student can attach a file and send a response.
- 🧩 The system dynamically loads all assignments relevant to the logged-in student.


<p align="center">
  <img src="https://github.com/user-attachments/assets/421da5ae-b603-4b88-b913-5319815fc465" alt="Submit Assignments Page" width="250"/>
</p>

---

### 🗂️ Registrar Dashboard

This is the main dashboard for registrars, providing centralized access to administrative operations related to students, teachers, subjects, and schedules.

#### Key Features:
- 👨‍🎓 **Student Management**:
  - Add Student
  - Edit Student
- 👩‍🏫 **Teacher Management**:
  - Add Teacher
  - Edit Teacher
- 🗓️ **Scheduling Tools**:
  - Add Student Schedule
  - Add Teacher Schedule
- 📘 **Subject Management**:
  - Add Subject


<p align="center">
  <img src="https://github.com/user-attachments/assets/f9474090-8be6-4265-bb65-25bc2e4e4168" alt="Registrar Dashboard" width="250"/>
</p>

---

### 🧾 Add New Student Page

This form enables the registrar to register a new student by collecting essential academic and personal details.

#### 📌 Student Form Fields:
- Full Name
- Email
- Password
- Birth Year (e.g., 2003)
- Major
- Level (e.g., Freshman)

The form is designed with simplicity and clarity to ensure fast and accurate data entry.

> ℹ️ **Note**: A similar form exists for adding teachers, which includes additional fields such as **department**, and other relevant teaching-related data.

<p align="center">
  <img src="https://github.com/user-attachments/assets/c0b8419d-516f-42f5-a53e-596f1576eb3f" alt="Add Student Page" width="250"/>
</p>

---
### 📝 Edit Student Page

This screen allows the registrar to update the information of an existing student. Once a specific **Student ID** is selected, the corresponding student data is automatically loaded into the form fields for editing.

#### 🔧 Editable Fields:
- Student ID (Selection)
- Full Name
- Email
- Birth Year
- Major
- Level

After making the necessary changes, clicking **"Save Changes"** updates the record in the database.

> ℹ️ **Note**: A similar form is available for editing teacher data, with additional fields such as **department**, **rank**, and possibly **office hours**.

<p align="center">
  <img src="https://github.com/user-attachments/assets/86b7208c-995d-4945-9a51-9e281902fc66" alt="Edit Student Page" width="250"/>
</p>

---
### 📆 Create Student Schedule Page

This screen allows the registrar to assign a class schedule for a specific student by selecting subject, day, time, and classroom location.

#### 🧩 Form Fields:
- Select Student ID
- Select Subject
- Select Day (e.g., Sunday, Monday...)
- Start Time (`HH:MM:SS`)
- End Time (`HH:MM:SS`)
- Classroom Location

After filling out the fields, clicking **"Create Schedule"** will assign the class session to the student.

> ℹ️ **Note**: A similar scheduling form is available for teachers, with the same structure and functionality.

<p align="center">
  <img src="https://github.com/user-attachments/assets/988b6ab6-4ae7-408b-8405-f96ac919cfa5" alt="Student Schedule Page" width="250"/>
</p>

---

### 📰 School Newsletter Page

This screen displays the latest announcements, highlights, and recognitions from the school community.

#### ✨ Example Content:
- 📸 **Teacher of the Month** spotlight with photo and biography
- 🗞️ Text-rich articles to honor accomplishments and share updates
- 🎉 Visual layout using card components to display multiple newsletter entries

The newsletter page is designed to engage students, teachers, and parents alike with dynamic and heartfelt school-related content.


<p align="center">
  <img src="https://github.com/user-attachments/assets/14333db8-d8b9-45e7-b413-f7dbc5d5e339" alt="Newsletter Page" width="250"/>
</p>


---

## 👨‍👩‍👧‍👦 Our Team

This project wasn’t just about code and screens — it was late-night laughs, teamwork, and shared "aha!" moments with amazing friends.

I had the privilege of working alongside some of the kindest and most driven people I know:

- **Yahya Abu Rayyan**  
- **Deema Abu Nimeh**  
- **Aseel Abd Alhaq**

Thanks for every idea, every bug we fixed, and every laugh we shared along the way.

---

## 🙏 Special Thanks

A heartfelt thank you to **Dr. Samer Zain** — not only for guiding us technically, but for inspiring us as a human being.

And as there is a sign in your office that carries the hadith of the Prophet Muhammad (peace be upon him):

> *"The best of people are those who are most beneficial to others."*  
> *"خير الناس أنفعهم للناس"*

— we truly felt that you lived by these words.  
You taught us every bit of **khayr** you knew, and we sincerely hope — *insha’Allah* — that you are among the people described in this beautiful hadith:

> *“Indeed, Allah, His angels, the inhabitants of the heavens and the earth — even the ant in its hole and the fish in the sea — send blessings upon the one who teaches people goodness.”*  
> *"إن الله وملائكته وأهل السموات والأرضين، حتى النملة في جحرها، وحتى الحوت؛ ليصلون على معلم الناس الخير"*

We sincerely testify that you have taught us **khayr** — not just in academics, but through your character, your inspiration, and the calm, humble way you carry yourself.



And as I once told you:  
> **"You are what doctors need to be."**

**Thank you.**
