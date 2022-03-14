var menuMobile = document.getElementById("menu-mobile")
var menuForm = document.getElementById("menu-form")
var nano = document.getElementById("nano")
var layoutWrapper = document.getElementById("layoutWrapper")

function clickShowMenu(){
    if(!layoutWrapper.classList.contains("layout-mobile-active")) {
        menuMobile.style.display = 'block'
    }
}
