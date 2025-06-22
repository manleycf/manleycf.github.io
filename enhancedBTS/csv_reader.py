from course import Course


# reads from a csv file
def csv_reader(path, tree):
    try:
        # takes parameter path as the file name
        with open(path, 'r') as file:
            # loops through each line, retrieving information
            for line in file:
                # splits the line at commas and holds information in parts
                parts = line.strip().split(',')
                # handles the possibility of prerequisites
                if len(parts) >= 2:
                    course = Course(*parts[:4])
                    tree.insert(course)
        print("Data successfully extracted from file.")
    # invalid file name
    except FileNotFoundError:
        print("File could not be opened.")