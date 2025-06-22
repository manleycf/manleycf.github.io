from course import Course


# Node class creates nodes with attached course, left and right children, and node height
class Node:
    def __init__(self, course):
        self.course = course
        self.left = None
        self.right = None
        self.height = 1


# Defines the tree structure
class AVLTree:
    # initialize with null root
    def __init__(self):
        self.root = None

    # gets height of a given node, else returns 0
    def _get_height(self, node):
        return node.height if node else 0

    # returns the balance value of a node by comparing the height of its children
    def _get_balance(self, node):
        return self._get_height(node.left) - self._get_height(node.right) if node else 0

    # handles left-left imbalances
    def _rotate_right(self, y):
        # first parent becomes right child of second node
        x = y.left
        z = x.right

        x.right = y
        y.left = z

        # update heights
        y.height = 1 + max(self._get_height(y.left), self._get_height(y.right))
        x.height = 1 + max(self._get_height(x.left), self._get_height(x.right))

        return x

    # handles right right imbalance
    def _rotate_left(self, x):
        # first parent becomes left child of second node
        y = x.right
        z = y.left

        y.left = x
        x.right = z

        # update heights
        x.height = 1 + max(self._get_height(x.left), self._get_height(x.right))
        y.height = 1 + max(self._get_height(y.left), self._get_height(y.right))

        return y

    # public insert method calls private insert from root node
    def insert(self, course):
        self.root = self._insert(self.root, course)

    # private insert method
    def _insert(self, node, course):
        # if tree is empty, insert at root
        if not node:
            return Node(course)

        # if course is smaller than node, move to left child
        if course.course_num < node.course.course_num:
            node.left = self._insert(node.left, course)
        # if course is larger than node, move to right child
        else:
            node.right = self._insert(node.right, course)

        # update height
        node.height = 1 + max(self._get_height(node.left), self._get_height(node.right))
        # find balance value for balancing logic
        balance = self._get_balance(node)

        # left left imbalance
        if balance > 1 and course.course_num < node.left.course.course_num:
            return self._rotate_right(node)

        # right right imbalance
        if balance < -1 and course.course_num > node.right.course.course_num:
            return self._rotate_left(node)

        # left right imbalance
        if balance > 1 and course.course_num > node.left.course.course_num:
            node.left = self._rotate_left(node.left)
            return self._rotate_right(node)

        # right left imbalance
        if balance < -1 and course.course_num < node.right.course.course_num:
            node.right = self._rotate_right(node.right)
            return self._rotate_left(node)

        # no rotation needed
        return node

    # public search method calls private search starting at root
    def search(self, course_num):
        return self._search(self.root, course_num)

    # private search
    def _search(self, node, course_num):
        # tree is empty:
        if not node:
            return Course()
        # course is found:
        if course_num == node.course.course_num:
            return node.course
        # course is smaller than node, move left:
        elif course_num < node.course.course_num:
            return self._search(node.left, course_num)
        # course is larger than node, move right:
        else:
            return self._search(node.right, course_num)

    # calls print method starting at root
    def print_in_order(self):
        self._in_order(self.root)

    # prints each node in tree
    def _in_order(self, node):
        if node:
            self._in_order(node.left)
            print(f"{node.course.course_num}, {node.course.title}")
            self._in_order(node.right)
