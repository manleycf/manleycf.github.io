# Course class holds course info (number, title, prerequisite)
class Course:
    def __init__(self, course_num, title, prereq=None, prereq2=None):
        self.course_num = course_num
        self.title = title
        self.prereq = prereq
        self.prereq2 = prereq2


# formats and prints course information
def display_course(course):
    print(f"{course.course_num}, {course.title}")
    if course.prereq and course.prereq2:
        print(f"Prerequisites: {course.prereq}, {course.prereq2}")
    elif course.prereq:
        print(f"Prerequisite: {course.prereq}")
    else:
        print(f"No prerequisites for {course.course_num}")