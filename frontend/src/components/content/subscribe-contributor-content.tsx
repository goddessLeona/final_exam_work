
import SubscribeContributorForm from "../forms/subscribeContributorForm";
import styles from "./subscribe-contributor-content.module.css"

export default function SubscribeContributorContent() {
  return (
    <div className={styles.container}>
      <h1>Become a contributor</h1>
      <h2 className={styles.subtitle}>
        Welcom new contributor.<br></br>
        We are happy to have you part of our pages.<br></br> 
        Before you can be part of the comunity and contribute with content, 
        you have to fill in this form
      </h2>
      <SubscribeContributorForm />
    </div>
  );
}
