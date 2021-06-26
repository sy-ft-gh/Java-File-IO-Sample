import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class FileInputOutput {
	public static void main(String[] args) throws Exception {
		List<String> buf = new ArrayList<String>();
		File file = null;
		try {
			/* ファイルの文字コードを指定して読み込む */
			file  = new File(".\\input.txt");
			if (!file.canRead()) {
				// fileクラスではファイルの読み込み、書き込みをチェックできる
				// 他にも、そのファイルが存在するか等などファイルに関するチェックがある
				System.out.println("読み込めません！");
				return;
			}
			// ファイルから入力(読み込み用)ストリームを取得し"Shift-JIS"の文字コード指定でフィルタする
			InputStreamReader  inputStreamReader =  new InputStreamReader(new FileInputStream(file),"Shift-JIS");
			// フィルタしたストリームリーダを更にバッファリング用のリーダで読み込む
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			try {
				/* 〜ファイル操作〜　*/
				String line = null;
				// リーダからの読み取り結果がなくなるまで行単位で読み出し
				while ((line = bufferedReader.readLine()) != null) { 
						buf.add(line);
						System.out.println("read:" + line);
				}
			} finally {
				// close処理はinputStreamReader、bufferedReaderいずれでもできるがいずれかで1回実行すればよい
				// 大本の入出力ストリームがいずれもcloseされるため
				bufferedReader.close();
			}
			// 文字コードを指定して書き込む
			file  = new File(".\\output2.txt");
			if (file.exists()) {
				// 既存ファイルがあり場合は削除
				if (!file.delete()) {
					System.out.println("書き込めません！");
					return;
				}
			}
			// ファイルから出力(書き込み用)ストリームを取得し"Shift-JIS"の文字コード指定でフィルタする
			// 文字コードを指定する
			OutputStreamWriter  outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"Shift-JIS");
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
			try {
				/* 〜ファイル操作〜　*/
				// 読み取った内容を行単位で書き出し
				while (buf.size() > 0) { 
					bufferedWriter.write(buf.get(0));
					bufferedWriter.newLine(); // newLineで改行を付加
					System.out.println("write:" + buf.get(0));
					buf.remove(0);
				}
			} finally {
				//ファイルをクローズする
				bufferedWriter.close();;
			}
						
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}