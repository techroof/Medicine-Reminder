package pillapp.ViewController;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pillapp.Model.Alarm;
import pillapp.Model.PillBox;
import teamqitalach.pillapp.R;

public class PDFActivity extends ActionBarActivity {
    String data;
    Paragraph paragraph;
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        Toast.makeText(this, "PDF saved to storage: resabea.pdf", Toast.LENGTH_LONG).show();
        Document document = new Document();
        PillBox pillBox = new PillBox();

        List<Alarm> alarms = null;
        //output file path
        String outpath = Environment.getExternalStorageDirectory() + "/resabea.pdf";

        List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday",
                "Wednesday", "Thursday", "Friday", "Saturday");
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outpath));
            document.open();
            for (int i = 1; i < 8; i++) {
                String day = days.get(i - 1);
                try {
                    alarms = pillBox.getAlarms(this, i);
                    Font font=new Font(Font.FontFamily.HELVETICA,18,Font.BOLD);
                    Paragraph p=new Paragraph(day,font);
                    p.setAlignment(Element.ALIGN_CENTER);
                    document.add(p);

                    if (alarms.size() != 0) {
                        for (Alarm alarm : alarms) {
                            Font font2=new Font(Font.FontFamily.HELVETICA,15,Font.NORMAL);
                            paragraph= new Paragraph(alarm.getPillName()+"        "
                                    +alarm.getStringTime()
                                    ,font2);
                            paragraph.setAlignment(Element.ALIGN_CENTER);
                            document.add(paragraph);
                            document.add(Chunk.NEWLINE);
                        }
                    } else {
                        Font font3=new Font(Font.FontFamily.HELVETICA,15,Font.NORMAL);
                        Paragraph p2=new Paragraph("You don't have any alarms for " + day + "." +
                                "",font3);
                        p2.setAlignment(Element.ALIGN_CENTER);
                       document.add(p2);
                        document.add(Chunk.NEWLINE);
                    }
                    document.add(Chunk.NEWLINE);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

            }
        document.close();
            Intent main=new Intent(PDFActivity.this, MainActivity.class);
            startActivity(main);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}




