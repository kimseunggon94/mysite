package kr.co.itcen.mysite.repository;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import kr.co.itcen.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	@Autowired
	private SqlSession sqlSession;
	
	private static final String SAVE_PATH = "/files";
	private static final String URL_PREFIX = "/files";

	
	public List<BoardVo> getList(int page, String kwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", (page-1)*5);
		List<BoardVo> result = null;
		
		if(kwd!=null && kwd.length() != 0) {
			map.put("kwd", "%"+kwd+"%");
			result = sqlSession.selectList("board.getListkwd", map);
		}else {
			result = sqlSession.selectList("board.getList", map);
		}
		
		return result;
	}
	
	public int getCount(String kwd) {
		int count =0;
		if(kwd!=null && kwd.length() != 0) {
			kwd = "%"+kwd+"%";
			count = sqlSession.selectOne("board.getCountkwd",kwd);
		}else {
			count = sqlSession.selectOne("board.getCount");
		}
		
		return count;
	}

	public BoardVo get(Long no) {
		return sqlSession.selectOne("board.get", no);
		
	}

	public boolean modify(BoardVo vo) {
		int count = sqlSession.update("board.update", vo);
		return count ==1;
		
	}

	public boolean delete(BoardVo vo) {
		int count = sqlSession.update("board.delete", vo);
		return count ==1;
		
	}

	public boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert", vo);
		return count ==1;
		
	}

	public boolean reply(BoardVo vo) {
		sqlSession.update("board.replyupdate", vo);
		int count = sqlSession.insert("board.reply", vo);
		return count ==1;
		
	}

	public void hit(Long no) {
		sqlSession.update("board.hit", no);
		
	}

	public String restore(MultipartFile multipartFile) {
		String url = "";
		try {
			if (multipartFile == null) {
				return url;
			}
			String originalFilename = multipartFile.getOriginalFilename();
			String saveFileName = generateSaveFilename(
					originalFilename.substring(originalFilename.lastIndexOf(".") + 1));
			long fileSize = multipartFile.getSize();
			
			
			byte[] fileData = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
			os.write(fileData);
			os.close();
			System.out.println("------------"+url);
			url = URL_PREFIX + "/" + saveFileName;
		} catch (IOException e) {
			throw new RuntimeException();
		}

		return url;
	}
		
	private String generateSaveFilename(String extName) {
		String filename = "";
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		return filename;
	}

}