import styles from "./home.module.css"
import { Inter, Finger_Paint } from "next/font/google"

const inter = Inter({
        subsets: ["latin"],
        weight: ["600"]
    });

const fingerPaint = Finger_Paint({
    subsets: ["latin"],
    weight: "400",
}); 

export default function Home() {
  
    return (
        <main className={styles.container}>

            <div className={styles.AboutMe}>

                <div className={styles.photo}>
                    <img src="petra.jpg" className={styles.sideImage}/>
                </div>

                <div className={styles.personal}>
                    <p>
                        Petra Johansson <br></br>
                        Fullstack -develop <br></br>
                        Teknikhögskolan 24/26
                    </p>
                </div>

            </div>

            <div className={styles.mvp}>
            
                <div>
                    <p className={`${fingerPaint.className} ${styles.title}`}>Welcome to my examen project</p> 
                    <p className={styles.frame1}>
                        In 2024, I started a two-year Fullstack Developer education. I began from zero, and although I have learned a lot during these two years, I still have a long way to go before fully understanding everything in the field.
                        This project is my final project after two years of learning...</p>
                </div>

                
            </div>
      </main>
    
  );
}