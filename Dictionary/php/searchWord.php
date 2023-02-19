<?php
    $conn = mysqli_connect("localhost","root","","dictionary")or die("Connection failed: " . mysqli_connect_error());
    
    $word = $_COOKIE['word'];

    $sql = "SELECT * FROM `data` WHERE `word`= '$word'";
    $result = mysqli_query($conn, $sql);
    mysqli_close($conn);
?>

<?php
$data = $result->fetch_assoc();
if(!$data) : ?>
  <script>
      update.hidden = true;
      del.hidden = true;
      </script>
    <style> .container { background-color: #d2daff; } </style>
    <h3 class="error" style='border-bottom: 3px solid darkolivegreen;'>Word not found!</h3>
  <?php  else:  ?>

<style> .container { background-color: #d2daff; } </style>
<div class="word">
    <h3><?php echo $data['word'] ?></h3>
</div>
<div class="details">
  <p><?php echo $data['type'] ?></p>
</div>
<p class="word-meaning"><?php echo $data['definition'] ?></p>
<p class="word-example"><?php echo $data['example'] ?></p>

<script>
  update.hidden = false;
  del.hidden = false;
</script>

<?php endif; ?>