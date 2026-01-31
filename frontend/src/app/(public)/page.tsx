
import styles from "./page.module.css";

export default function Home() {
  return (
    <div className={styles.page}>
      <main>
        <div className={styles.introText}>
        <h1>Welcome to my examen project</h1> 
        <p>A member-based platform for members and contributors.</p>
        </div>
        <div className={styles.intro}>
          <h1>MVP-Requirements</h1>
          <p className= {styles.textTitles}>Content & Access</p>
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

          <p className= {styles.textTitles}>Discovery & Search</p>  
          <div className= {styles.box}>
            <ul>
              <li>Members can search for content by specific contributor</li>
              <li>Members can search for content by tags (e.g. vacation, year, nature)</li>
            </ul>
          </div>
          <p className= {styles.textTitles}>Technical Stack</p>   
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
      </main>
    </div>
  );
}
