// CS-300 Project 2
// Christopher Manley

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>

using namespace std;

// structure holds course information
struct Course {
    string courseNum;
    string title;
    string prereq;
    string prereq2;
};

// structure for tree nodes
struct Node {
    Course course;
    Node *left;
    Node* right;
    
    Node() {
        left = nullptr;
        right = nullptr;
    }
    Node(Course aCourse) :
        Node() {
        course = aCourse;
    }
};

// binary search tree class definition
class BinarySearchTree {

private:
    Node* root;
    void addNode(Node* node, Course course);
    void inOrder(Node* node);

public:
    BinarySearchTree();
    void InOrder();
    void Insert(Course course);
    Course Search(string courseNum);
};

// default constructor for tree
BinarySearchTree::BinarySearchTree() {
    root = nullptr;
}

//starts the ordered print with the root
void BinarySearchTree::InOrder() {
    inOrder(root);
}

// insert courses into tree
void BinarySearchTree::Insert(Course course) {
    // if tree is empty
    if (root == nullptr) {
        root = new Node(course);
    }
    else {
        addNode(root, course);
    }
}

// searches courses
Course BinarySearchTree::Search(string courseNum) {
    Node* current = root;
    while (current != nullptr) {
        // found correct course
        if (current->course.courseNum == courseNum) {
            return current->course;
        }
        // if course is smaller go left
        else if (courseNum < current->course.courseNum) {
            current = current->left;
        }
        // if course is larger go right
        else {
            current = current->right;
        }
    }
    // if course not found return empty course
    Course course;
    return course;
}

// add course to a node
void BinarySearchTree::addNode(Node* node, Course course) {
    // if course is smaller add to left
    if (course.courseNum < node->course.courseNum) {
        // if left node is empty add there
        if (node->left == nullptr) {
            node->left = new Node(course);
        }
        // else continue down left node path
        else {
            addNode(node->left, course);
        }
    }
    // else course is larger go right
    else {
        // if right node empty add there
        if (node->right == nullptr) {
            node->right = new Node(course);
        }
        // else continue down right node path
        else {
            addNode(node->right, course);
        }
    }
}

// go down tree in order, printing information
void BinarySearchTree::inOrder(Node* node) {
    if (node != nullptr) {
        inOrder(node->left);
        // print info
        cout << node->course.courseNum << ", " << node->course.title << endl;
        inOrder(node->right);
    }
}

// prints results of a search
void displayCourse(Course course) {
    cout << course.courseNum << ", " << course.title << endl;
    // if course has prerequisites
    if (course.prereq != "") {
        // if course has 2 prerequisites
        if (course.prereq2 != "") {
            cout << "Prerequisites: " << course.prereq << ", " << course.prereq2 << endl;
        }
        else {
            cout << "Prerequisite: " << course.prereq << endl;
        }
    }
    else {
        cout << "No prerequisites for " << course.courseNum << endl;
    }
}

// opens and reads data from csv file
void csvReader(string csvPath, BinarySearchTree* bst) {
    ifstream file(csvPath);
    if (!file.is_open()) {
        cout << "File could not be opened." << endl;
        return;
    }
    string line;
    while (getline(file, line)) {
        stringstream ss(line);
        Course course;

        getline(ss, course.courseNum, ',');
        getline(ss, course.title, ',');
        getline(ss, course.prereq, ',');
        getline(ss, course.prereq2);

        bst->Insert(course);
    }
    file.close();

    cout << "Data successfully extracted from file." << endl;
}

int main()
{
    BinarySearchTree* bst;
    bst = new BinarySearchTree();
    Course course;
    string fileName = "0";
    string searchNum;

    cout << "Welcome to the course planner." << endl;

    int choice = 0;
    while (choice != 9) {
        cout << "" << endl;
        cout << "Menu" << endl;
        cout << "1. Load Data Structure" << endl;
        cout << "2. Print Course List" << endl;
        cout << "3. Print Course" << endl;
        cout << "9. Exit" << endl;
        cout << "" << endl;
        cout << "What would you like to do?" << endl;

        cin >> choice;

        switch (choice) {

        // get input from file
        case 1:
            cout << "To use the default input file, type '1'. Otherwise, input the name of the file." << endl;
            cin >> fileName;
            if (fileName == "1") {
                // loads default file
                csvReader("input.txt", bst);
            }
            else {
                csvReader(fileName, bst);
            }
            break;

        // print course list alphanumerically
        case 2:
            bst->InOrder();
            break;

        // search course
        case 3:
            cout << "Please enter the course number you wish to search." << endl;
            cin >> searchNum;
            course = bst->Search(searchNum);
            // if course is found print info
            if (!course.courseNum.empty()) {
                displayCourse(course);
            }
            // if course not found
            else {
                cout << "Course number '" << searchNum << "' not found." << endl;
            }

            break;
        }
    }

    cout << "Good bye." << endl;

}
