document.addEventListener('DOMContentLoaded', function() {
    // 네비게이션 기능
    const navLinks = document.querySelectorAll('.nav-links li');
    const pages = document.querySelectorAll('.page');

    navLinks.forEach(link => {
        link.addEventListener('click', () => {
            navLinks.forEach(l => l.classList.remove('active'));
            link.classList.add('active');

            const pageId = `${link.dataset.page}-page`;
            pages.forEach(page => {
                if (page.id === pageId) {
                    page.classList.remove('hidden');
                } else {
                    page.classList.add('hidden');
                }
            });
        });
    });

    // 검색 기능
    const searchBars = document.querySelectorAll('.search-bar');
    searchBars.forEach(searchBar => {
        const input = searchBar.querySelector('input');
        const container = searchBar.closest('.page').querySelector('.cards-container');
        input.addEventListener('input', (e) => searchItems(e.target.value, container));
    });

    // // 물건 추가 버튼 처리
    // document.querySelector('.add-item-btn').addEventListener('click', () => {
    //     window.location.href = '/item/add';
    // });

    // 삭제 기능
    const deleteButtons = document.querySelectorAll('.delete-user, .delete-item');
    const deleteModal = document.getElementById('deleteModal');
    const confirmDelete = document.getElementById('confirmDelete');
    const cancelDelete = document.getElementById('cancelDelete');
    const deleteTargetName = document.getElementById('deleteTargetName');
    let currentDeleteForm = null;

    // 삭제 버튼 클릭시
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            e.stopPropagation();
            
            const row = button.closest('tr');
            const name = button.getAttribute('data-id');
            const isUser = button.classList.contains('delete-user');
            const isFood = button.classList.contains('delete-food');
            
            // 삭제 대상 텍스트 설정
            if (isFood) {
                deleteTargetName.textContent = `음식 "${name}"을(를)`;
            } else {
                deleteTargetName.textContent = isUser 
                    ? `회원 "${name}"을(를)` 
                    : `물건 "${name}"을(를)`;
            }
            
            currentDeleteForm = button.closest('form');
            deleteModal.classList.remove('hidden');
        });
    });

    // 삭제 확인
    confirmDelete.addEventListener('click', function() {
        if (currentDeleteForm) {
            currentDeleteForm.submit();
        }
        deleteModal.classList.add('hidden');
    });

    // 삭제 취소
    cancelDelete.addEventListener('click', () => {
        deleteModal.classList.add('hidden');
        currentDeleteForm = null;
    });

    // 모달 외부 클릭시 닫기
    deleteModal.addEventListener('click', function(e) {
        if (e.target === deleteModal) {
            deleteModal.classList.add('hidden');
            currentDeleteForm = null;
        }
    });

    // 알림 메시지 자동 제거
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.remove();
        }, 5000); // 5초 후 제거
    });

    // 테이블 페이지네이션 기능 초기화
    if (document.querySelector('.food-table')) {
        initFoodTable();
        initFoodSearch(); // 검색 기능 초기화
    }
});

// 검색 기능
function searchItems(searchTerm, container) {
    const cards = container.querySelectorAll('.card');
    searchTerm = searchTerm.toLowerCase().trim();

    cards.forEach(card => {
        const text = card.textContent.toLowerCase();
        if (text.includes(searchTerm)) {
            card.style.display = 'flex';
        } else {
            card.style.display = 'none';
        }
    });
}

// 검색 결과 메시지 관련 함수
function showNoResultsMessage(container, searchTerm) {
    removeNoResultsMessage(container);
    const message = document.createElement('div');
    message.className = 'no-results-message';
    message.innerHTML = `
        <i class="material-icons">search_off</i>
        <p>"${searchTerm}"에 대한 검색 결과가 없습니다.</p>
        <small>다른 검색어로 시도해 보세요.</small>
    `;
    container.appendChild(message);
}

function removeNoResultsMessage(container) {
    const existingMessage = container.querySelector('.no-results-message');
    if (existingMessage) existingMessage.remove();
}

function filterCards(container, searchText) {
    const cards = container.querySelectorAll('.card');
    const noResults = container.querySelector('.no-results');
    let hasResults = false;

    cards.forEach(card => {
        const title = card.querySelector('h3').textContent.toLowerCase();
        if (title.includes(searchText.toLowerCase())) {
            card.style.display = '';
            hasResults = true;
        } else {
            card.style.display = 'none';
        }
    });

    // 검색 결과 없음 메시지 표시/숨김
    if (hasResults) {
        noResults.classList.add('hidden');
    } else {
        noResults.classList.remove('hidden');
    }
}

// 테이블 페이지네이션 기능
function initFoodTable() {
    const table = document.querySelector('.food-table');
    const rowsPerPageSelect = document.getElementById('rowsPerPage');
    const pagination = document.getElementById('pagination');
    let currentPage = 1;
    let rowsPerPage = parseInt(rowsPerPageSelect.value);

    function displayTableData() {
        const rows = table.querySelectorAll('tbody tr');
        const start = (currentPage - 1) * rowsPerPage;
        const end = start + rowsPerPage;

        rows.forEach((row, index) => {
            if (index >= start && index < end) {
                row.style.display = '';
            } else {
                row.style.display = 'none';
            }
        });

        updatePagination(rows.length);
    }

    function updatePagination(totalRows) {
        const pageCount = Math.ceil(totalRows / rowsPerPage);
        pagination.innerHTML = '';

        // 이전 페이지 버튼
        if (pageCount > 1) {
            const prevButton = document.createElement('button');
            prevButton.innerHTML = '<i class="material-icons">chevron_left</i>';
            prevButton.disabled = currentPage === 1;
            prevButton.onclick = () => {
                if (currentPage > 1) {
                    currentPage--;
                    displayTableData();
                }
            };
            pagination.appendChild(prevButton);
        }

        // 페이지 번호 버튼들
        for (let i = 1; i <= pageCount; i++) {
            if (
                i === 1 || 
                i === pageCount || 
                (i >= currentPage - 2 && i <= currentPage + 2)
            ) {
                const button = document.createElement('button');
                button.textContent = i;
                button.classList.toggle('active', i === currentPage);
                button.onclick = () => {
                    currentPage = i;
                    displayTableData();
                };
                pagination.appendChild(button);
            } else if (
                i === currentPage - 3 || 
                i === currentPage + 3
            ) {
                const ellipsis = document.createElement('span');
                ellipsis.textContent = '...';
                ellipsis.className = 'ellipsis';
                pagination.appendChild(ellipsis);
            }
        }

        // 다음 페이지 버튼
        if (pageCount > 1) {
            const nextButton = document.createElement('button');
            nextButton.innerHTML = '<i class="material-icons">chevron_right</i>';
            nextButton.disabled = currentPage === pageCount;
            nextButton.onclick = () => {
                if (currentPage < pageCount) {
                    currentPage++;
                    displayTableData();
                }
            };
            pagination.appendChild(nextButton);
        }
    }

    // 페이지당 행 수 변경 이벤트
    rowsPerPageSelect.addEventListener('change', () => {
        rowsPerPage = parseInt(rowsPerPageSelect.value);
        currentPage = 1;
        displayTableData();
    });

    // 초기 테이블 표시
    displayTableData();
}

// 음식 검색 기능
function initFoodSearch() {
    const foodSearchInput = document.querySelector('#foods-page .search-bar input');
    const foodTable = document.querySelector('.food-table');
    const noResultsMessage = document.createElement('div');
    noResultsMessage.className = 'no-results hidden';
    noResultsMessage.innerHTML = `
        <i class="material-icons">search_off</i>
        <p>검색 결과가 없습니다.</p>
        <small>다른 검색어로 시도해 보세요.</small>
    `;
    foodTable.parentElement.appendChild(noResultsMessage);

    foodSearchInput.addEventListener('input', function(e) {
        const searchTerm = e.target.value.toLowerCase().trim();
        const rows = foodTable.querySelectorAll('tbody tr');
        let hasResults = false;

        rows.forEach(row => {
            const name = row.querySelector('.food-name').textContent.toLowerCase();
            if (name.includes(searchTerm)) {
                row.style.display = '';
                hasResults = true;
            } else {
                row.style.display = 'none';
            }
        });

        // 검색 결과가 없을 때 메시지 표시
        noResultsMessage.classList.toggle('hidden', hasResults);
        
        // 페이지네이션 업데이트
        if (searchTerm === '') {
            displayTableData(); // 기존 페이지네이션 로직 사용
        } else {
            pagination.innerHTML = ''; // 검색 중에는 페이지네이션 숨김
        }
    });
}