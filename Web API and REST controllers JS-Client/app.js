// let loadBooksBtn = document.getElementById('loadBooks');

// loadBooksBtn.addEventListener('click', loadAllBooks)

// // async function loadAllBooks() {
// //     const booksResponse = await fetch('')
// //     const booksData = await booksResponse.json();
// //     console.log(booksData)
// // }

// function loadAllBooks() {
//     debugger
//     fetch('https://localhost:8080/api/books/')
//     .then(response => response.json())
//     .then(json => json.forEach(element => {
//         console.log(element)
//     }));
// }
function attachEvents() {
    const BASE_URL = 'http://localhost:8080/api/books/'
    // debugger

    const loadAllBooksBtn = document.getElementById('loadBooks');
    const tableBody = document.querySelector('tbody');

    const titleInput = document.querySelector('input[name="title"]');
    const authorInput = document.querySelector('input[name="author"]');
    const isbnInput = document.querySelector('input[name="isbn"]');
    const submitBtn = document.getElementById('form-button');

    const formHeader = document.querySelector('form h3');
    let editBookId = null;

    loadAllBooksBtn.addEventListener('click', loadAllBooks);
    submitBtn.addEventListener('click', submitBtnHandler);

    // async function loadAllBooks() {
    //     const booksResponse = await fetch(BASE_URL)
    //     const booksData = await booksResponse.json();
    //     createTable(booksData);
    // }
    function loadAllBooks() {
        tableBody.innerHTML = '';
        fetch(BASE_URL)
        .then((res) => res.json())
        .then((booksData) => createTable(booksData))
    }

    async function submitBtnHandler() {
        const title = titleInput.value;
        const author = authorInput.value;
        const isbn = isbnInput.value;

        const isValidInput = title != '' && author != '';

        if(isValidInput) {
            let url = BASE_URL;

            const httpHeaders = {
                method: 'POST',
                body: JSON.stringify({title, author, isbn})
            }

            if(formHeader.textContent === 'Edit FORM') {
                httpHeaders.method = 'PUT';
                url += editBookId;
            }

            const createBookResponse = await fetch(url, httpHeaders)
            const bookData = await createBookResponse.json();
            loadAllBooks();
            if(formHeader.textContent === 'Edit FORM') {
                formHeader.textContent = 'FORM';
                submitBtn.textContent = 'Submit'
            }
            titleInput.value = '';
            authorInput.value = '';
        }
    }

    function createTable(booksData) {
       
        for (const book in booksData) {
        
            const title = booksData[book].title;
            const author = booksData[book].author;
            const isbn = booksData[book].isbn;
            const bookID = booksData[book].id;

            const tr = document.createElement('tr');
        

            const titleTd = document.createElement('td');
            titleTd.textContent = title;

            const authorTd = document.createElement('td');
            authorTd.textContent = author.name;

            const isbnTd = document.createElement('td')
            isbnTd.textContent = isbn;

            const buttonsTd = document.createElement('td');

            const editBtn = document.createElement('button');
            editBtn.textContent = 'Edit';
            editBtn.addEventListener('click', () => {
                titleInput.value = title;
                authorInput.value = author.name;
                formHeader.textContent = 'Edit FORM';
                submitBtn.textContent = 'Save';
                editBookId = bookID;
            });
            const deleteBtn = document.createElement('button');
            deleteBtn.textContent = 'Delete';

            deleteBtn.addEventListener('click', async () => {
                const httpHeaders = {
                    method: 'DELETE'
                };
                await fetch(BASE_URL + bookID, httpHeaders);
                debugger
                loadAllBooks();
            });

            buttonsTd.appendChild(editBtn);
            buttonsTd.appendChild(deleteBtn);

            tr.appendChild(titleTd);
            tr.appendChild(authorTd);
            tr.appendChild(buttonsTd);

            tableBody.appendChild(tr);
        }
    }
}

attachEvents();