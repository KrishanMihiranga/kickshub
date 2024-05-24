$(document).ready(function() {
  const toast = $(".toast");
  const toastError = $(".toast-error");
  const closeIcon = $(".close");
  const progress = $(".progress");
  const progressError = $(".progress-error");
  let timer1, timer2;

  closeIcon.on("click", function() {
    toast.removeClass("activeToast");
    toastError.removeClass("activeToast");

    setTimeout(function() {
      progress.removeClass("activeToast");
      progressError.removeClass("activeToast");
    }, 300);

    clearTimeout(timer1);
    clearTimeout(timer2);
  });

  function showSuccess(text){
    $('.text-2').text(text);
    toast.addClass("activeToast");
    progress.addClass("activeToast");

    timer1 = setTimeout(function() {
      toast.removeClass("activeToast");
    }, 5000);

    timer2 = setTimeout(function() {
      progress.removeClass("activeToast");
    }, 5300);
  }

  function showError(text){
    $('.text-4').text(text);
    toastError.addClass("activeToast");
    progressError.addClass("activeToast");

    timer1 = setTimeout(function() {
      toastError.removeClass("activeToast");
    }, 5000);

    timer2 = setTimeout(function() {
      progressError.removeClass("activeToast");
    }, 5300);
  }

  window.showSuccess = showSuccess;
  window.showError = showError;
});
