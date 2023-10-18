# CS211-661-Project
Project ภาคต้น 2566



# Project detail
https://saacsos.notion.site/Project-CS211-2566-1c8e195c0ebd4071aea8f733692e3989?pvs=4


# วิธีการติดตั้งหรือรันโปรแกรม
1.git clone https://github.com/CS211-661/cs211-661-project-tofu.git
2. เปิดโฟลเดอร์ TofuApplication
3.เปิด tofu-application-mac.jar หากเปิดจาก MacOS และ tofu-application-win.jar หากเปิดจาก Window
*หมายเหตุ หากเไม่สามารถปิดไฟล์ tofu-application-mac.jar ได้จาก MacOS ให้เปิดผ่าน terminal java -jar tofu-application-mac.jar
*หากต้องการดูรายละเอียดของ event ที่หน้า main **ให้คลิ๊ก 2 ครั้ง

# บัญชี
- บัญชีที่สร้างอีเว้นท์
    - username: jett
    - password: jett11
    - username: หนุ่มกะลา
    - password: numnum
    - username: RattaRathalos
    - password: ratrat
    - username: Messi007
    - password: mess
- บัญชีแอดมิน
    - username: admin
    - password: admin


# โครงสร้างไฟล์
- project-tofu/
- data/
    - images/
    - .csv
- src/
    - main/
        - java/
            - cs211/project/
                - controllers/
                    - creator/
                    - event/
                    - login/
                    - main/
                    - team/
                - cs211661project/
                    - HelloApplication
                - models/
                    - account/
                    - collections/
                    - event/
                - services/
        - resources/
            - cs211/project/views (ไฟล์ fxml)
            - values/
                - styles (เก็บไฟล์ css)




## สรุปสิ่งที่พัฒนา

### ภูเบศ สิรเมธาวุฒิ 6510450810
### Github Username: BessX9073, Phubes Sirimaytawut
- 9.a ระบุวิธีการใช้งานเพื่ออธิบายวิธีการใช้งานโปรแกรม
- 10.a ตรวจสอบความถูกต้องของข้อความที่ปรากฏในส่วนต่าง ๆ
- 10.b Graphic User Interface (GUI) มีรูปแบบที่เข้าใจง่าย
- 10.c ขนาดหน้าจอของโปรแกรมต้องมีความสูงไม่เกิน 1024 pixel มีความกว้างของหน้าจอโปรแกรมเหมาะสําหรับผู้ใช้โน้ตบุ๊กทั่วไป
- 11.b ข้อมูลคําสั่งหรือคําแนะนําในการใช้โปรแกรมที่นิสิตสร้างขึ้นมา
- 16.b ผู้ใช้ระบบสามารถเปลี่ยนรหัสผ่านของตนเองได้
- 16.c ผู้ใช้ระบบสามารถเปลี่ยนรูปภาพเพื่อใช้เป็นภาพของผู้ใช้ระบบ หากผู้ใช้ไม่เปลี่ยนหรือยังไม่กำหนดภาพ ระบบจะให้ใช้ภาพ default แทนไปก่อน
- 17.a มีหน้าแสดงรายการอีเวนต์ที่อยู่ระหว่างการจัดงานทั้งหมดให้ผู้ใช้ทั่วไปดู โดยแสดงภาพอีเวนต์ ชื่ออีเวนต์ และจำนวนที่ว่างเหลือสำหรับเข้าร่วมอีเวนต์
- 17.b มีหน้าแสดงประวัติรายการอีเวนต์ที่ตนเองเข้าร่วม โดยแยกอีเวนต์ที่อยู่ระหว่างการจัดงาน และอีเวนต์ที่สิ้นสุดแล้ว
- 17.c มีส่วนให้ค้นหาอีเวนต์ด้วยบางส่วนของชื่ออีเวนต์
- 21.b มีหน้าแสดง และ หน้าจัดการ (เพิ่มและลบ)  “ตารางกิจกรรมของอีเวนต์สำหรับผู้ร่วมทีม<ชื่อทีม>”
- 21.c ผู้จัดอีเวนต์กำหนดได้ว่า กิจกรรมใดเสร็จสิ้นไปแล้ว

### ธภัทร จิรเมธารัตน์ 6510450453
### Github Username: DEASII
- 10.a ตรวจสอบความถูกต้องของข้อความที่ปรากฏในส่วนต่าง ๆ
- 10.b Graphic User Interface (GUI) มีรูปแบบที่เข้าใจง่าย
- 10.c ขนาดหน้าจอของโปรแกรมต้องมีความสูงไม่เกิน 1024 pixel มีความกว้างของหน้าจอโปรแกรมเหมาะสําหรับผู้ใช้โน้ตบุ๊กทั่วไป
- 16.a ข้อมูลการลงทะเบียนได้แก่
- 17.a มีหน้าแสดงรายการอีเวนต์ที่อยู่ระหว่างการจัดงานทั้งหมดให้ผู้ใช้ทั่วไปดู โดยแสดงภาพอีเวนต์ ชื่ออีเวนต์ และจำนวนที่ว่างเหลือสำหรับเข้าร่วมอีเวนต์
- 17.b มีหน้าแสดงประวัติรายการอีเวนต์ที่ตนเองเข้าร่วม โดยแยกอีเวนต์ที่อยู่ระหว่างการจัดงาน และอีเวนต์ที่สิ้นสุดแล้ว
- 17.d ผู้ใช้สามารถเข้าร่วมอีเวนต์ได้หากมีที่ว่างเหลืออยู่ หากไม่มีที่ว่างเหลือต้องไม่ให้เข้าร่วม
- 18.a มีส่วนให้ผู้ใช้ทั่วไปสร้างอีเวนต์ เพื่อเป็นผู้จัดอีเวนต์ (Create)
- 18.b มีหน้าแสดงรายการอีเวนต์ที่ตนเองเป็นผู้จัดอีเวนต์ (Edit)
- 19.b มีหน้าแก้ไขข้อมูลอีเวนต์ (ข้อมูลตามข้อ 18.a)
- 19.e มีส่วนจัดการ “การเปิดรับทีมผู้ร่วมจัดอีเวนต์”
- 20.a มีหน้าแสดงรายชื่อผู้เข้าร่วมอีเวนต์ ผู้จัดอีเวนต์สามารถระงับสิทธิ์ผู้เข้าร่วมอีเวนต์ได้
- 20.c มีหน้าแสดง และหน้าจัดการ (เพิ่มและลบ) “ตารางกิจกรรมของอีเวนต์สำหรับผู้เข้าร่วมอีเวนต์”
- 21.a มีหน้าแสดงทีมผู้ร่วมจัดอีเวนต์ และแสดงรายชื่อผู้ร่วมทีมในทีมนั้น ผู้จัดอีเวนต์สามารถระงับสิทธิ์ผู้ร่วมทีมได้ ผู้เข้าร่วมอีเวนต์ที่ถูกระงับสิทธิ์จะไม่เห็น
- 21.c ผู้จัดอีเวนต์กำหนดได้ว่า กิจกรรมใดเสร็จสิ้นไปแล้ว

### พศวัต คำภีระ 6510450704
### Github Username: GustPK
-  9.a ระบุวิธีการใช้งานเพื่ออธิบายวิธีการใช้งานโปรแกรม
-  9.b ระบุตัวอย่างข้อมูลของ Username และ Password ในการเข้าใช้งานระบบของแต่ละ role
-  9.c ระบุรายละเอียด CSV
- 10.a ตรวจสอบความถูกต้องของข้อความที่ปรากฏในส่วนต่าง ๆ
- 10.b Graphic User Interface (GUI) มีรูปแบบที่เข้าใจง่าย
- 10.c ขนาดหน้าจอของโปรแกรมต้องมีความสูงไม่เกิน 1024 pixel มีความกว้างของหน้าจอโปรแกรมเหมาะสําหรับผู้ใช้โน้ตบุ๊กทั่วไป
- 11.b ข้อมูลคําสั่งหรือคําแนะนําในการใช้โปรแกรมที่นิสิตสร้างขึ้นมา
- 16.b ผู้ใช้ระบบสามารถเปลี่ยนรหัสผ่านของตนเองได้
- 21.a มีหน้าแสดงทีมผู้ร่วมจัดอีเวนต์ และแสดงรายชื่อผู้ร่วมทีมในทีมนั้น ผู้จัดอีเวนต์สามารถระงับสิทธิ์ผู้ร่วมทีมได้ ผู้เข้าร่วมอีเวนต์ที่ถูกระงับสิทธิ์จะไม่เห็น
- 21.b มีหน้าแสดง และ หน้าจัดการ (เพิ่มและลบ)  “ตารางกิจกรรมของอีเวนต์สำหรับผู้ร่วมทีม<ชื่อทีม>”
- 21.c ผู้จัดอีเวนต์กำหนดได้ว่า กิจกรรมใดเสร็จสิ้นไปแล้ว
- 22.a ผู้เข้าทีมเห็นชื่อทีม และ “ตารางกิจกรรมของอีเวนต์สำหรับผู้ร่วมทีม<ชื่อทีม>” สามารถแสดงความคิดเห็นภายในกิจกรรม และ สามารถดูความคิดเห็นภายในกิจกรรมและโต้ตอบกันได้


### ณฐนนท์ ภูธนกิจ 6510450313
### Github Username: TheZeroNXD
- 10.b Graphic User Interface (GUI) มีรูปแบบที่เข้าใจง่าย
- 10.c ขนาดหน้าจอของโปรแกรมต้องมีความสูงไม่เกิน 1024 pixel มีความกว้างของหน้าจอโปรแกรมเหมาะสําหรับผู้ใช้โน้ตบุ๊กทั่วไป
- 11.a ข้อมูลนิสิตผู้จัดทําโปรแกรม
- 15.b ผู้ดูแลระบบสามารถเปลี่ยนรหัสผ่านของตนเองได้ หลังจาก Login โดยต้องระบุ password เดิมที่ถูกต้องด้วย พร้อมระบุรหัสผ่านใหม่ การยืนยันรหัสใหม่ที่ตรงกัน จึงจะเปลี่ยนรหัสผ่านได้ และรหัสผ่านใหม่ต้องใช้ได้
- 15.c มีหน้าแสดงรายชื่อของผู้ใช้ระบบ โดยต้องแสดงภาพของผู้ใช้ ชื่อสำหรับเข้าสู่ระบบ (username) ชื่อผู้ใช้ระบบ และวันเวลาที่เข้าใช้ล่าสุดของผู้ใช้ระบบ เรียงลำดับตามวันเวลาที่เข้าใช้ระบบล่าสุดก่อน
- 16.c ผู้ใช้ระบบสามารถเปลี่ยนรูปภาพเพื่อใช้เป็นภาพของผู้ใช้ระบบ หากผู้ใช้ไม่เปลี่ยนหรือยังไม่กำหนดภาพ ระบบจะให้ใช้ภาพ default แทนไปก่อน
- 17.a มีหน้าแสดงรายการอีเวนต์ที่อยู่ระหว่างการจัดงานทั้งหมดให้ผู้ใช้ทั่วไปดู โดยแสดงภาพอีเวนต์ ชื่ออีเวนต์ และจำนวนที่ว่างเหลือสำหรับเข้าร่วมอีเวนต์
- 17.d ผู้ใช้สามารถเข้าร่วมอีเวนต์ได้หากมีที่ว่างเหลืออยู่ หากไม่มีที่ว่างเหลือต้องไม่ให้เข้าร่วม
- 18.a มีส่วนให้ผู้ใช้ทั่วไปสร้างอีเวนต์ เพื่อเป็นผู้จัดอีเวนต์ (Create)
- 19.c มีส่วนจัดการ “การเปิดรับผู้เข้าร่วมอีเวนต์” มีจำนวนเข้าร่วมสูงสุด และช่วงเวลาเริ่มต้น-สิ้นสุดที่จะเปิดรับผู้เข้าร่วมอีเวนต์
- 
