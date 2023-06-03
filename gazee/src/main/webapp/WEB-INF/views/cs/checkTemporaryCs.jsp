<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">

    var sessionId = "<%=session.getAttribute("id")%>";
    //임시저장이 된(temporary가 0인) cs가 있으면 임시저장된것을 불러올지 임시저장한 cs를 삭제할지 묻고 임시저장을 불러온다하면csUpdate로 아니면 csDelete로처리하고 csWrite.jsp로 이동
	if (${result} === 1) {
    // 임시저장된 cs가 있을 경우 처리할 내용
    if (confirm("작성 중인 글이 있습니다. 이어서 작성하시겠습니까? 취소를 누르면 자동으로 삭제되고 새로운 글을 쓸 수 있습니다.")) {
        // 이어서 작성하도록 처리하는 페이지로 이동
        location.href = "goToCsUpdate?csWriter="+sessionId+"&id="+${bag.csId};
    } else {
        // 임시저장된 cs를 삭제하는 페이지로 이동
        
        if (confirm('정말로 삭제하시겠습니까?')) {
            $.ajax({
                url: '../cs/csDelete',
                type: 'POST',
                data: {
                    csWriter: sessionId,
                    csId: ${bag.csId}
                },
                success: function(x) {
                        alert('삭제되었습니다.');
                        location.href = "csList?page=1&mode=1";
                }
            })//ajax
        }
     }
} else {
    // 임시저장된 product가 없을 경우 register.jsp로 이동
    location.href = "goToCsWrite?csWriter="+sessionId;
}
	   
</script>