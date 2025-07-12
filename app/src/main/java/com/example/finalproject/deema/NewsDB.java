package com.example.finalproject.deema;

import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class NewsDB {
    private static final ArrayList<News> newslist = new ArrayList<>();
    public NewsDB() {
        newslist.add(new News(R.drawable.soccerteam,"We are thrilled to announce and celebrate the outstanding achievement of our school soccer team!"+
                "\n" +
                "With determination, discipline, and true team spirit, our players have brought home a remarkable victory. Every match played, every challenge faced, and every goal scored has made us incredibly proud to be part of this school community.\n" +
                "\n" +
                "This win is more than just a trophy—it's a reflection of the hard work, sportsmanship, and unity that our team has shown throughout the season.\n" +
                "\n" +
                "Congratulations to our champions—you’ve made us proud!\n" +
                "\n" +
                "Let’s all take a moment to celebrate this success and continue to support each other in striving for excellence, on and off the field.","Our schools soccer champs"));
        newslist.add(new News(R.drawable.spellbees,"We are proud to recognize the incredible achievement of our Spelling Bee champions! \n" +
                "With sharp minds, strong focus, and tireless preparation, our students have shown exceptional talent and dedication in this year’s competition. Their hard work and love for learning have led them to a well-deserved victory.\n" +
                "\n" +
                "Winning the Spelling Bee is no small feat—it takes confidence, courage, and a true passion for words. Our champions have inspired us all with their determination and academic excellence.\n" +
                "\n" +
                "Congratulations to our spelling stars—you’ve made us all proud!\n" +
                "\n" +
                "Let’s celebrate their success and continue to encourage one another to reach for excellence in everything we do.","Our schools spelling bees champs"));

        newslist.add(new News(R.drawable.girlsbasketteam,"We are beyond proud to celebrate the incredible success of our All-Girls Basketball Team!" +
                "\n" +
                "Through skill, teamwork, and unwavering determination, these amazing athletes have shown what it truly means to play with heart. Their recent victory is a shining example of what can be achieved through hard work, dedication, and unity.\n" +
                "\n" +
                "Every pass, every shot, and every defensive play reflected their passion and strength—not just as players, but as role models for our entire school community.\n" +
                "\n" +
                "Congratulations, champions! You’ve inspired us all and made history with your performance.\n" +
                "\n" +
                "Let’s celebrate their success and continue to uplift and support one another—on and off the court.","Our schools Basketball champs"));

        newslist.add(new News(R.drawable.chessteam,"We are proud to announce the outstanding achievement of our Chess Team, made up of both boys and girls who have demonstrated exceptional talent and determination.\n" +
                "\n" +
                "Through sharp thinking, strategic planning, and true teamwork, our students have secured a well-earned victory. Every match they played showcased their discipline, focus, and deep understanding of the game.\n" +
                "\n" +
                "This success is not just a win on the chessboard—it reflects the hard work, dedication, and unity that our team brings to every competition. They have made our school proud and inspired others to strive for excellence.\n" +
                "\n" +
                "Congratulations to our Chess Champions! Your success is a shining example of what can be accomplished when we work and grow together." ,"Our schools Chess champs"));
        newslist.add(new News(R.drawable.greenteam,"We are excited to recognize the remarkable efforts and accomplishments of our Environmental Green Team.\n" +
                "\n" +
                "This dedicated group of students has worked tirelessly to promote sustainability, raise environmental awareness, and lead initiatives that make a real difference in our school and community. From organizing clean-up drives to reducing waste and encouraging eco-friendly habits, their impact has been both inspiring and meaningful.\n" +
                "\n" +
                "Their recent achievements are a reflection of their passion, leadership, and commitment to protecting our planet. They have shown us that change begins with action—and that every small effort counts.\n" +
                "\n" +
                "Congratulations to our Green Team! You have made our school community proud and set an example for others to follow.\n" +
                "\n" +
                "Let’s continue to support and grow these efforts together, as we work toward a cleaner, greener future.","Our schools Green team"));

        newslist.add(new News(R.drawable.teacherofthemonth,"We are proud to honor and celebrate our Teacher of the Month, whose dedication, passion, and commitment to excellence have made a lasting impact on our school community.\n" +
                "\n" +
                "This outstanding educator goes above and beyond in the classroom every day—fostering a love of learning, inspiring students to reach their full potential, and creating a positive and inclusive environment for all.\n" +
                "\n" +
                "Their hard work, creativity, and unwavering support for students and colleagues alike have not gone unnoticed. This recognition is a small reflection of the deep appreciation we have for their efforts.\n" +
                "\n" +
                "Congratulations to our Teacher of the Month! Your leadership and heart continue to shape our school in the best possible way.\n" +
                "\n" +
                "Thank you for all that you do."
                ,"Our schools teacher of the month"));

        newslist.add(new News(R.drawable.waterfountains,"We are pleased to highlight a simple yet important part of our daily school life—our school water fountains.\n" +
                "\n" +
                "Clean, cold, and accessible water is essential for staying healthy, focused, and energized throughout the day. Thanks to ongoing care and recent improvements, our water fountains continue to serve as a reliable resource for students and staff alike.\n" +
                "\n" +
                "Whether it’s refilling a bottle or taking a quick drink between classes, our fountains support wellness, reduce plastic waste, and encourage sustainable habits across campus.\n" +
                "\n" +
                "Let’s all do our part to take care of this valuable resource by using it responsibly and keeping the areas around it clean.\n" +
                "\n" +
                "Here’s to staying hydrated and staying green, one sip at a time."

                ,"Our schools water fountains"));

        newslist.add(new News(R.drawable.debteam,"We are proud to recognize the outstanding achievement of our Debate Team, whose recent performance has demonstrated the power of critical thinking, confidence, and articulate expression.\n" +
                "\n" +
                "With strong arguments, thoughtful perspectives, and respectful engagement, our students have shown what it means to listen, lead, and speak with purpose. Their preparation, teamwork, and poise under pressure have brought great pride to our school.\n" +
                "\n" +
                "Whether presenting compelling cases or responding with clarity and insight, each member of the team represented our values of integrity, knowledge, and respect.\n" +
                "\n" +
                "Congratulations to our Debate Team! Your voices have made an impact, and your success is a reflection of your hard work and dedication.\n" +
                "\n" +
                "Let’s continue to support and celebrate the strength of student voice in our school community."

                ,"Our schools debate team"));

    }
    public List<News> getNews() {
        List<News> result = new ArrayList<>();

        for(News b: newslist){

            result.add(b);
        }
        return result;

    }
}
