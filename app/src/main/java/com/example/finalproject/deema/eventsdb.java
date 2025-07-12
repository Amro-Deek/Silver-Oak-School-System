package com.example.finalproject.deema;


import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class eventsdb {
    private static final ArrayList<Event> eventslist = new ArrayList<>();

    public eventsdb() {
        eventslist.add(new Event(
                "15",
                "SAT",
                "May 15 @ 10:00 am",
                "Class President Election (Grade 12)",
                "Our senior students are stepping up to lead! Join us for the Grade 12 Class President elections. Candidates will present their vision, and students will vote for their next leader. Let's support leadership and student voice.",
                R.drawable.classpres
        ));

        eventslist.add(new Event(
                "18",
                "TUE",
                "May 18 @ 12:00 pm",
                "Open Day Celebration",
                "Our school opens its doors to families and community members. Discover classroom projects, meet teachers, and enjoy performances. A fun and informative event for everyone!",
                R.drawable.openday
        ));

        eventslist.add(new Event(
                "21",
                "FRI",
                "May 21 @ 11:30 am",
                "Annual Bake Sale Fundraiser",
                "Support our school clubs and activities by purchasing homemade treats during our Bake Sale! All proceeds go to student initiatives and events. Don’t forget to bring your sweet tooth!",
                R.drawable.bakesale
        ));

        eventslist.add(new Event(
                "24",
                "MON",
                "May 24 @ 7:00 pm",
                "Movie Night Under the Stars",
                "Bring a blanket, some snacks, and enjoy a movie screening outdoors with your friends and family. A night full of fun, laughter, and popcorn awaits!",
                R.drawable.movienight
        ));

        eventslist.add(new Event(
                "27",
                "THU",
                "May 27 @ 6:30 pm",
                "Senior Prom Night",
                "It’s time to shine! Join us for a magical evening of music, dancing, and celebration as we honor our graduating class with an unforgettable prom night.",
                R.drawable.promnight
        ));

        eventslist.add(new Event(
                "29",
                "SAT",
                "May 29 @ 8:00 am",
                "Annual School Field Trip Day",
                "An exciting day of exploration and learning outside the classroom. Whether it's nature, museums, or science centers, get ready for a fun and educational adventure!",
                R.drawable.fieldtrip
        ));

        eventslist.add(new Event(
                "31",
                "MON",
                "May 31 @ 10:00 am",
                "School Weather Cast Team Elections",
                "Do you have a passion for forecasting and presenting? Students will vote for this year’s weather team, who will report school weather updates with flair and fun!",
                R.drawable.voting
        ));

        eventslist.add(new Event(
                "3",
                "THU",
                "June 3 @ 9:00 am",
                "Science Fair Exhibition",
                "Come see the incredible innovations and experiments our students have been working on. A showcase of creativity, discovery, and scientific passion!",
                R.drawable.science
        ));
    }


    public List<Event> getEvents() {
        return new ArrayList<>(eventslist);
    }
}
