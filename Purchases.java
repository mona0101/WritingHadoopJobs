import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Purchases {

    public static class PurchasesMapper extends Mapper<Object, Text, Text,Text >{
        private  boolean isHeader = true; // To handle header row

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            String[] data = value.toString().split(",");

            if (isHeader) {
                isHeader = false; // Skip the first row (header)
                return;
            }

            String BuyerID = data[1];
            float purchPrice;

            try {
                purchPrice = Float.parseFloat(data[2]);
            } catch (NumberFormatException e) {
                System.out.println("Number Format Exception");
                return;
            }

            context.write(new Text(BuyerID), new Text("1,"+String.format("%.2f", purchPrice)));

        }
    }

    public static class PurchaseReducer extends Reducer<Text,Text, Text, Text> {
        private Text result = new Text();

        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            int CountPurch = 0;
            float purchPrice = 0;


            for (Text value : values) {
                String[] parts = value.toString().split(",");
                CountPurch += Integer.parseInt(parts[0]);
                purchPrice += Float.parseFloat(parts[1]);

            }


            context.write(key, new Text(   CountPurch+","+ String.format("%.2f", purchPrice)));
        }
    }


    public void debug(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Number of purchases and Count");
        job.setJarByClass(Purchases.class);

        job.setMapperClass(PurchasesMapper.class);
        job.setCombinerClass(PurchaseReducer.class);
        job.setReducerClass(PurchaseReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Number of purchases and Count");
        job.setJarByClass(Purchases.class);

        job.setMapperClass(PurchasesMapper.class);
        job.setCombinerClass(PurchaseReducer.class);
        job.setReducerClass(PurchaseReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}