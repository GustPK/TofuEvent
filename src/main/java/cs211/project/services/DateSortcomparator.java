package cs211.project.services;

import cs211.project.models.event.Event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class DateSortcomparator implements Comparator<Event> {
    @Override
    public int compare(Event o1, Event o2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate d1 = LocalDate.parse(o1.getDateStart(),formatter);
        LocalDate d2 = LocalDate.parse(o2.getDateStart(),formatter);
        return d1.compareTo(d2);
    }
}
