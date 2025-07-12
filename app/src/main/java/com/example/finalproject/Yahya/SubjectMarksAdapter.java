package com.example.finalproject.Yahya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.List;

public class SubjectMarksAdapter extends RecyclerView.Adapter<SubjectMarksAdapter.ViewHolder> {

    public static class Mark {
        public String examType;
        public double mark;

        public Mark(String examType, double mark) {
            this.examType = examType;
            this.mark = mark;
        }
    }

    public static class SubjectWithMarks {
        public String subjectName;
        public List<Mark> marks;

        public SubjectWithMarks(String subjectName, List<Mark> marks) {
            this.subjectName = subjectName;
            this.marks = marks;
        }

        public double getAverageMark() {
            if (marks == null || marks.isEmpty()) return 0.0;
            double sum = 0;
            for (Mark mark : marks) sum += mark.mark;
            return sum / marks.size();
        }
    }

    private Context context;
    private List<SubjectWithMarks> subjects;

    public SubjectMarksAdapter(Context context, List<SubjectWithMarks> subjects) {
        this.context = context;
        this.subjects = subjects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_marks_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SubjectWithMarks subject = subjects.get(position);
        holder.subjectName.setText(subject.subjectName);
        holder.gradeValue.setText("Grade: " + Math.round(subject.getAverageMark()));
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName, gradeValue;

        public ViewHolder(View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subjectName);
            gradeValue = itemView.findViewById(R.id.gradeValue);
        }
    }
}
