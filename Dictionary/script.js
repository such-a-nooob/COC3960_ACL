const result = document.getElementById("result");
const search = document.getElementById("search-btn");
const add = document.getElementById("add-btn");
const update = document.getElementById("update-btn");
const del = document.getElementById("del-btn");

search.addEventListener("click", () => {
  let inpWord = document.getElementById("word").value;
  document.cookie = "word=" + inpWord;
  $.ajax({
    type: "GET",
    url: "php/searchWord.php",
    dataType: "html",
    success: function (data) {
      $("#result").html(data);
    }
  });
})

add.addEventListener("click", () => {
  let inpWord = document.getElementById("word");
  add.hidden = true;
  search.hidden = true;
  update.hidden = true;
  inpWord.hidden = true;
  del.hidden = true;
  result.innerHTML = `
            <style> .container { background-color: #d2daff; } </style>
            <form id="addForm" method="post" action="php/addWord.php">
            <div class="input">
            <input type="text" placeholder="Word" id="new-word" name="new-word" required />
            <input type="text" placeholder="Word type" id="type" name="type" required />
            <input type="text" placeholder="Definition" id="def" name="def" required />
            <input type="text" placeholder="Example" name="example" />
            </div>
            <button type="submit" class="function" value="add" id="sub-add" >Add</button>
            </form>
            `;

  let submit = document.getElementById('sub-add');
  submit.addEventListener("click", () => {
    $.post($("#addForm").attr("action"),
      $("#addForm :input").serializeArray());

    let $word = document.getElementById('new-word').value;
    let $type = document.getElementById('type').value;
    let $def = document.getElementById('def').value;
    if ($word && $type && $def) {
      alert(document.getElementById('new-word').value + " is added to the dictionary!");
      window.location.reload();
    }
    else
      alert("Fill the required fields!");
  });

  $("#addForm").submit(function () {
    return false;
  });

});

update.addEventListener("click", () => {
  let inpWord = document.getElementById("word");
  add.hidden = true;
  search.hidden = true;
  inpWord.hidden = true;
  update.hidden = true;
  del.hidden = true;
  result.innerHTML = `
            <style> .container { background-color: #d2daff; } </style>
            <div class="word">
              <h3>${inpWord.value}</h3>
            </div>
            <form id="upForm" method="post" action="php/updateWord.php">
            <div class="input">
            <input type="text" name="word" value="${inpWord.value}" hidden />
            <input type="text" placeholder="Word type" id="type" name="type" required />
            <input type="text" placeholder="Definition" id="def" name="def" required />
            <input type="text" placeholder="Example" name="example" />
            </div>
            <button class="function" value="update" id="sub-update">Update</button>
            </form>
            `;
  let submit = document.getElementById('sub-update');
  submit.addEventListener("click", () => {
    $.post($("#upForm").attr("action"),
      $("#upForm :input").serializeArray());

    let $type = document.getElementById('type').value;
    let $def = document.getElementById('def').value;
    if ($type && $def) {
      alert(inpWord.value + " is updated in the dictionary!");
      window.location.reload();
    }
    else
      alert("Fill the required fields!");
  });

  $("#upForm").submit(function () {
    return false;
  });

});

del.addEventListener("click", () => {
  let inpWord = document.getElementById("word").value;
  document.cookie = "word=" + inpWord;

  $.ajax({
    type: "GET",
    url: "php/delWord.php",
    dataType: "html",
  });

  alert(inpWord + " is deleted from the dictionary!");
  window.location.reload();
});
