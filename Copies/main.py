from AVL import AVLTree
from course import display_course
from csv_reader import csv_reader


# main function holds menu options and routing logic
def main():
    # start the tree
    tree = AVLTree()
    print("Welcome to the course planner.")

    # loop keeps menu open until user selects 9
    while True:
        print("\nMenu")
        print("1. Load Data Structure")
        print("2. Print Course List")
        print("3. Print Course")
        print("9. Exit")

        # get user choice of menu options (without spaces)
        choice = input("What would you like to do? ").strip()

        # invalid choice handling
        if choice not in {"1", "2", "3", "9"}:
            print("Invalid choice. Please enter 1, 2, 3, or 9.")
            continue

        # load tree with info from CSV
        if choice == "1":
            file_name = input("To use the default input file, type '1'. Otherwise, input the name of the file.")
            # default file
            if file_name == "1":
                csv_reader("input.txt", tree)
            # custom file
            else:
                csv_reader(file_name, tree)

        # print entire tree, sorted alphanumerically
        elif choice == "2":
            tree.print_in_order()

        # print a single course from search
        elif choice == "3":
            search_num = input("Please enter the course number you wish to search: ")
            course = tree.search(search_num)
            # course is found:
            if course.course_num:
                display_course(course)
            # no course found:
            else:
                print(f"Course number '{search_num}' not found.")

        # exit option
        elif choice == "9":
            print("Good bye.")
            break


if __name__ == "__main__":
    main()