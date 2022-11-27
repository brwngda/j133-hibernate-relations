package pl.sda.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.sda.hibernate.HibernateUtil;
import pl.sda.hibernate.model.Ocena;
import pl.sda.hibernate.model.Student;

import java.util.Scanner;

public class Main_usunOcene {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // pytamy użytkownika czy chce usunąc ocene czu studenta
            System.out.println("Co chcesz usunac: (ocena / student)");
            String encjaUsuwana = scanner.nextLine();
            if (encjaUsuwana.equalsIgnoreCase("ocena")) {
                // jeśli odpowie ocene, to pytamy o id oceny
                System.out.println("Podaj id oceny, która chesz usunąć:");
                String id = scanner.nextLine();
                Long ocenaId = Long.parseLong(id);

                // następnie usuwamy ocenę (jeśli została znaleziona)
                Ocena ocena = session.get(Ocena.class, ocenaId);
                if (ocena != null) {
                    session.remove(ocena);
                } else {
                    System.err.println("Ocena nie istnieje");
                }
            } else if (encjaUsuwana.equalsIgnoreCase("student")) {
                // jeśli odpowie student, to pytamy o id studenta
                System.out.println("Podaj id studenta, którego chesz usunąć:");
                String id = scanner.nextLine();
                Long studentId = Long.parseLong(id);

                // następnie szukamy studenta
                Student student = session.get(Student.class, studentId);
                if (student != null) {
                    // jeśli nie ma ocen, to go usuwamy
                    if (!student.getOceny().isEmpty()) {
                        // jeśli ma oceny to usuwamy najpierw oceny...
                        for (Ocena ocena : student.getOceny()) {
                        session.remove(ocena);
                    }
                }
                // ... potem studenta
                session.remove(student);
                } else {
                    System.err.println("Student nie istnieje");
                }
            }

            transaction.commit();
        } catch (Exception ioe) {
            // jeśli złapiemy błąd, to wywoła się catch
            System.err.println("Błąd bazy: " + ioe);
        }
    }

}
