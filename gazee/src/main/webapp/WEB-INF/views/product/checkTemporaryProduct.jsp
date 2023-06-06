<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
    var sessionId = "<%=session.getAttribute("id")%>";
    //임시저장이 된(temporary가 0인) product가 있으면 임시저장된것을 불러올지 임시저장한 product를 삭제할지 묻고 임시저장을 불러온다하면 productUpdateSel로 아니면 productDelete로처리하고 register.jsp로 이동
    console.log(${bag.temporary});
	if (${result} === 1) {
    // 임시저장된 product가 있을 경우 처리할 내용
    if (confirm("작성 중인 판매 정보가 있습니다. 이어서 작성하시겠습니까? 취소를 누르면 자동으로 삭제되고 새로운 판매글을 쓸 수 있습니다.")) {
        // 이어서 작성하도록 처리하는 페이지로 이동
        location.href = "../product/productUpdate.jsp?memberId=" + sessionId + "&productId=" + ${bag.productId};
    } else {
        // 임시저장된 product를 삭제하는 페이지로 이동
        
        if (confirm('정말로 삭제하시겠습니까?')) {
            $.ajax({
                url: 'productDelete',
                data: {
                    memberId: sessionId,
                    productId: ${bag.productId}
                },
                success: function(x) {
                    alert('삭제되었습니다.');
                    location.href = "../product/register.jsp?memberId=" + sessionId;
                }
            });
        }
    }
} else {
    // 임시저장된 product가 없을 경우 register.jsp로 이동
    location.href = "../product/register.jsp?memberId=" + sessionId;
}
</script>
