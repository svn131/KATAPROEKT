const sidebar = document.createElement("aside");
sidebar.innerHTML = `
  <div>
    <ul>
      <li><a href="#">Главная</a></li>
      <li>ПУБЛИЧНЫЕ</li>
      <ul>
        <li><a href="#">Вопросы</a></li>
        <li><a href="#">Метки</a></li>
        <li><a href="#">Участники</a></li>
        <li><a href="#">Неотвеченные</a></li>
      </ul>
    </ul>
  </div>
`;
document.body.insertBefore(sidebar, document.querySelector("main"));

