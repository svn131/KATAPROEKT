const header = document.createElement("header");
header.innerHTML = `
  <nav>
    <img src="../logo.png" alt="логотип">
    <input type="text" placeholder="поиск">
    <a href="#">Справка</a>
    <a href="#">Список сайтов</a>
    <a href="#">Войти</a>
    <a href="#">Зарегистрироваться</a>
  </nav>
`;
document.body.prepend(header);
