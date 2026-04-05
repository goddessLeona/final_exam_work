
import styles from "./page.module.css";

export default function Home() {
  return (
    <div className={styles.page}>
      <main>
        <div className={styles.container}>
          <div className={styles.introText}>
          <h1>Welcome to my examen project</h1> 
          <p>A member-based platform for members and contributors.</p>
          </div>
          <div>
            <h1 className={styles.intro}>MVP-Requirements</h1>
            <h1 className= {styles.textTitles}>Content & Access</h1>
            <div className= {styles.box}>
              <ul>
                <li>Contributors can upload content from their dashboard pages to the member pages</li>
                <li>Contributors also have access to member pages</li>
                <li>Members only have access to member pages</li>
                <li>Contributors must be approved by an admin before gaining upload access</li>
                <li>If uploaded content includes more than one contributor:
                <ul>
                  <li>The content should appear on each relevant contributor’s dashboard</li>
                  <li>Any contributor associated with the content should be able to remove it from their dashboard</li>
                </ul>
                </li>
              </ul>
            </div>

            <h1 className={styles.textTitles}>Discovery & Search</h1>  
            <div className= {styles.box}>
              <ul>
                <li>Members can search for content by specific contributor</li>
                <li>Members can search for content by tags (e.g. vacation, year, nature)</li>
              </ul>
            </div>
            <h1 className= {styles.textTitles}>Technical Stack</h1>   
            <div className= {styles.box}>
              <ul>
                <li>Backend: Java Spring Boot</li>
                <li>Authentication: Spring Security (JWT)</li>
                <li>Database: PostgreSQL</li>
                <li>Migration. Flyway</li>
                <li>Frontend: Next.js</li>
              </ul>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
