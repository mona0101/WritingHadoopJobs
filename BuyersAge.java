
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class BuyersAge {
    public static class AgeFilter extends Mapper<Object, Text, Text, Text>{
        private boolean isHeader = true; // To handle header row
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String record = value.toString();
            if (isHeader) {
                isHeader = false; // Skip the first row (header)
                return;
            }

            String[] data = record.split(",");
            String id =data[0].trim();
            String name =data[1].trim();
            String gender =data[3].trim();;
            String salary =data[4].trim();;

            try {
                int age = Integer.parseInt(data[2].trim());
                if (age >= 20 && age <= 50) {
                    context.write(new Text(id),
                            new Text(name + "," + age + "," + gender + "," + salary));
                }
            } catch (NumberFormatException e) {
                System.out.println("Number Format Exception");

            }
        }
    }


    public void debug(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Buyers whose Age between 20 and 50");
        job.setJarByClass(BuyersAge.class);
        job.setMapperClass(AgeFilter.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Buyers whose Age between 20 and 50");
        job.setJarByClass(BuyersAge.class);
        job.setMapperClass(AgeFilter.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}