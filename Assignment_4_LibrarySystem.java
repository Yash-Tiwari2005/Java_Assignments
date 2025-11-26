import java.io.*;
import java.util.*;

class Book implements Comparable<Book> {
    private int bookId;
    private String title;
    private String author;
    private String category;
    private boolean isIssued;

    public Book(int bookId, String title, String author, String category, boolean isIssued) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isIssued = isIssued;
    }

    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public boolean isIssued() { return isIssued; }

    public void markAsIssued() { isIssued = true; }
    public void markAsReturned() { isIssued = false; }

    public void displayBookDetails() {
        System.out.println("Book ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Category: " + category);
        System.out.println("Issued: " + (isIssued ? "Yes" : "No"));
    }

    public String toFileString() {
        return bookId + "," + title + "," + author + "," + category + "," + isIssued;
    }

    public int compareTo(Book b) {
        return this.title.compareToIgnoreCase(b.title);
    }
}

class Member {
    private int memberId;
    private String name;
    private String email;
    private List<Integer> issuedBooks;

    public Member(int memberId, String name, String email, List<Integer> issuedBooks) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.issuedBooks = issuedBooks;
    }

    public int getMemberId() { return memberId; }
    public List<Integer> getIssuedBooks() { return issuedBooks; }

    public void addIssuedBook(int bookId) { issuedBooks.add(bookId); }

    public void returnIssuedBook(int bookId) { issuedBooks.remove(Integer.valueOf(bookId)); }

    public void displayMemberDetails() {
        System.out.println("Member ID: " + memberId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.print("Issued Books: ");
        for (int b : issuedBooks) System.out.print(b + " ");
        System.out.println();
    }

    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(memberId).append(",").append(name).append(",").append(email).append(",");
        for (int id : issuedBooks) sb.append(id).append(":");
        return sb.toString();
    }
}

public class LibrarySystem {
    private Map<Integer, Book> books = new HashMap<>();
    private Map<Integer, Member> members = new HashMap<>();
    private Set<String> categories = new HashSet<>();
    private Scanner sc = new Scanner(System.in);

    public void loadFromFile() {
        try {
            File f1 = new File("books.txt");
            if (!f1.exists()) f1.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader("books.txt"));
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] arr = line.split(",");
                int id = Integer.parseInt(arr[0]);
                Book b = new Book(id, arr[1], arr[2], arr[3], Boolean.parseBoolean(arr[4]));
                books.put(id, b);
                categories.add(arr[3]);
            }
            br.close();
        } catch (Exception e) {}

        try {
            File f2 = new File("members.txt");
            if (!f2.exists()) f2.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader("members.txt"));
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] arr = line.split(",");
                int id = Integer.parseInt(arr[0]);
                String[] issuedArr = arr.length > 3 ? arr[3].split(":") : new String[0];
                List<Integer> issuedList = new ArrayList<>();
                for (String x : issuedArr) if (!x.isEmpty()) issuedList.add(Integer.parseInt(x));
                Member m = new Member(id, arr[1], arr[2], issuedList);
                members.put(id, m);
            }
            br.close();
        } catch (Exception e) {}
    }

    public void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("books.txt"));
            for (Book b : books.values()) {
                bw.write(b.toFileString());
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {}

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("members.txt"));
            for (Member m : members.values()) {
                bw.write(m.toFileString());
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {}
    }

    public void addBook() {
        try {
            System.out.print("Enter Book Title: ");
            String title = sc.nextLine();
            System.out.print("Enter Author: ");
            String author = sc.nextLine();
            System.out.print("Enter Category: ");
            String category = sc.nextLine();

            int id = books.size() + 101;
            Book b = new Book(id, title, author, category, false);
            books.put(id, b);
            categories.add(category);
            saveToFile();
            System.out.println("Book added successfully with ID: " + id);
        } catch (Exception e) {
            System.out.println("Error adding book.");
        }
    }

    public void addMember() {
        try {
            System.out.print("Enter Member Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            int id = members.size() + 1;
            Member m = new Member(id, name, email, new ArrayList<>());
            members.put(id, m);
            saveToFile();
            System.out.println("Member added with ID: " + id);
        } catch (Exception e) {
            System.out.println("Error adding member.");
        }
    }

    public void issueBook() {
        System.out.print("Enter Book ID: ");
        int bid = sc.nextInt();
        System.out.print("Enter Member ID: ");
        int mid = sc.nextInt();
        sc.nextLine();

        if (!books.containsKey(bid) || !members.containsKey(mid)) {
            System.out.println("Invalid Book or Member ID.");
            return;
        }

        Book b = books.get(bid);
        if (b.isIssued()) {
            System.out.println("Book already issued.");
            return;
        }

        b.markAsIssued();
        members.get(mid).addIssuedBook(bid);
        saveToFile();
        System.out.println("Book issued successfully.");
    }

    public void returnBook() {
        System.out.print("Enter Book ID: ");
        int bid = sc.nextInt();
        System.out.print("Enter Member ID: ");
        int mid = sc.nextInt();
        sc.nextLine();

        if (!books.containsKey(bid) || !members.containsKey(mid)) {
            System.out.println("Invalid Book or Member ID.");
            return;
        }

        Book b = books.get(bid);
        Member m = members.get(mid);

        if (!m.getIssuedBooks().contains(bid)) {
            System.out.println("This member has not issued this book.");
            return;
        }

        b.markAsReturned();
        m.returnIssuedBook(bid);
        saveToFile();
        System.out.println("Book returned successfully.");
    }

    public void searchBooks() {
        System.out.print("Enter keyword (title/author/category): ");
        String key = sc.nextLine().toLowerCase();
        for (Book b : books.values()) {
            if (b.getTitle().toLowerCase().contains(key) ||
                b.getAuthor().toLowerCase().contains(key) ||
                b.getCategory().toLowerCase().contains(key)) {
                b.displayBookDetails();
                System.out.println();
            }
        }
    }

    public void sortBooks() {
        System.out.println("1. Sort by Title");
        System.out.println("2. Sort by Author");
        int c = sc.nextInt();
        sc.nextLine();

        List<Book> list = new ArrayList<>(books.values());

        if (c == 1) Collections.sort(list);
        else if (c == 2) {
            Collections.sort(list, new Comparator<Book>() {
                public int compare(Book a, Book b) {
                    return a.getAuthor().compareToIgnoreCase(b.getAuthor());
                }
            });
        }

        for (Book b : list) {
            b.displayBookDetails();
            System.out.println();
        }
    }

    public void menu() {
        loadFromFile();
        while (true) {
            System.out.println("\nWelcome to City Library Digital Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books");
            System.out.println("6. Sort Books");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1: addBook(); break;
                case 2: addMember(); break;
                case 3: issueBook(); break;
                case 4: returnBook(); break;
                case 5: searchBooks(); break;
                case 6: sortBooks(); break;
                case 7: saveToFile(); System.out.println("Goodbye!"); return;
                default: System.out.println("Invalid choice");
            }
        }
    }

    public static void main(String[] args) {
        new LibrarySystem().menu();
    }
}
