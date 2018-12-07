import java.util.*;

class NonExistingItemException extends RuntimeException {
    public NonExistingItemException(int id) {
        super(String.format("Item with id [" + id + "] doesn't exist"));
    }
}

class ArchiveStore {
    private List<Archive> archives;
    private StringBuilder log;

    public ArchiveStore() {
        archives = new ArrayList<>();
        log = new StringBuilder();
    }

    void archiveItem(Archive item, Date date) {
        item.setDateArchived(date);
        archives.add(item);
        log.append("Item [" + item.getId() + "] archived at [" + date + "]");
    }

    void openItem(int id, Date date) throws NonExistingItemException {
        Archive arch = archives.stream()
                .filter(archive -> archive.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NonExistingItemException(id));
        log.append(arch.doOpen(id, date));
    }

    public String getLog() {
        return log.toString();
    }
}

abstract class Archive {
    private int id;
    private Date dateArchived;

    public Archive(int id) {
        this.id = id;
    }

    public void setDateArchived(Date dateArchived) {
        this.dateArchived = dateArchived;
    }

    public int getId() {
        return id;
    }

    public abstract String doOpen(int id, Date date);
}

class LockedArchive extends Archive {
    private Date dateToOpen;

    public LockedArchive(int id, Date dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    @Override
    public String doOpen(int id, Date date) {
        String logMsg;
        if (date.before(dateToOpen)) {
            logMsg = "Item [" + id + "] opened at [" + date + "]";
        } else {
            logMsg = "Item [" + id + "] cannot be opened before [" + date + "]";
        }
        return logMsg;
    }
}

class SpecialArchive extends Archive {
    private int timesOpened;
    private int maxOpen;

    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.timesOpened = 0;
        this.maxOpen = maxOpen;
    }

    @Override
    public String doOpen(int id, Date date) {
        String logMsg;
        if (timesOpened < maxOpen) {
            logMsg = "Item [" + id + "] opened at [" + date + "]";
            timesOpened++;
        } else {
            logMsg = "Item [" + id + "] cannot be opened more than [" + maxOpen + "] times";
        }
        return logMsg;
    }
}

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
                    * 60 * 1000));
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while (scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch (NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}
