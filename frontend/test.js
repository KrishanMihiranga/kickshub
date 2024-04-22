const imgDiv =document.querySelector('.user-img');
const img =document.querySelector('#profile-picture-employee');
const file =document.querySelector('#employee-file');
const employeeDpUploadBtn =document.querySelector('#emp-file-upload-btn');

file.addEventListener('change', function(){
    const choosedFile = this.files[0];
    if(choosedFile){
        const reader = new FileReader();

        reader.addEventListener('load', function(){
            img.setAttribute('src', reader.result);
        });
        reader.readAsDataURL(choosedFile);
    }
});
