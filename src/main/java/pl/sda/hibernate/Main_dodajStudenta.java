package pl.sda.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.sda.hibernate.model.Student;

import java.util.Scanner;

public class Main_dodajStudenta {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Dodaj studenta
        //  - zapytaj użytkownika o:
        //      - imie
        System.out.println("Podaj imie:");
        String imie = scanner.nextLine();

        //      - nazwisko
        System.out.println("Podaj nazwisko:");
        String nazwisko = scanner.nextLine();

        //      - rok rozpoczęcia studiów
        System.out.println("Podaj rok rozpoczęcia studiów:");
        String rokStudiow = scanner.nextLine();
        int rokRozpoczeciaStudiow = Integer.parseInt(rokStudiow);

        Student student = Student.builder()
                .rokRozpoczeciaStudiow(rokRozpoczeciaStudiow)
                .nazwisko(nazwisko)
                .imie(imie)
                .build();

        //  Następnie dodaj studenta do bazy.
        try(Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();

            session.persist(student);

            transaction.commit();
        }catch (Exception e){
            System.err.println("Błąd dodawania studenta do bazy");
        }
    }
}